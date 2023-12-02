use legalandgeneral;
drop table if exists roles;
drop table if exists users_roles;
drop table if exists form_requests;
drop table if exists self_assessment;


drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;


 -- create a sequence for generating the answer  id
CREATE SEQUENCE self_assessment_seq START WITH 1;
CREATE TABLE self_assessment (
            id BIGINT PRIMARY KEY,
            responder_id VARCHAR(45) NOT NULL,
            form_id VARCHAR(45) NOT NULL,
            one_one VARCHAR(255),
            one_two VARCHAR(255),
            one_three VARCHAR(255),
            one_four VARCHAR(255),
            two_one VARCHAR(255),
            two_two VARCHAR(255),
            two_three VARCHAR(255),
            two_four VARCHAR(255),
            three_one VARCHAR(255),
            three_two VARCHAR(255),
            three_three VARCHAR(255),
            three_four VARCHAR(255),
            four_one VARCHAR(255),
            four_two VARCHAR(255),
            four_three VARCHAR(255),
            four_four VARCHAR(255),
            five_one VARCHAR(255),
            five_two VARCHAR(255),
            five_three VARCHAR(255),
            five_four VARCHAR(255),
            five_five VARCHAR(255),
            six_one VARCHAR(255),
            six_two VARCHAR(255),
            six_three VARCHAR(255),
            six_four VARCHAR(255),
            superpower VARCHAR(255),
            impact VARCHAR(255)
);

-- form requests table

Create TABLE IF NOT EXISTS form_requests (
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_status VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (Id)
);

-- users and roles for security -----------------------------

drop table if exists users;
create table if not exists users (
    id int not null auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    category_id int not null default 1,
    enabled boolean not null default true
)engine=InnoDB;

drop table if exists roles;
CREATE TABLE if not exists roles
(
    role_id int(11)     NOT NULL AUTO_INCREMENT primary key,
    name    varchar(45) NOT NULL
) engine = InnoDB;

CREATE TABLE if not exists users_roles
(
    id       bigint auto_increment primary key,
    username varchar(50) NOT NULL,
    role_id  int(11)     NOT NULL
) engine = InnoDB;

CREATE VIEW if not exists user_authorities as
select u.username as username, CONCAT("ROLE_", r.name) as authority
from users u
         inner join users_roles ur on u.username = ur.username
         inner join roles r on ur.role_id = r.role_id;


-- -------------------------------------------------------------


-- creating a table to store reviewers

CREATE TABLE if not exists reviewers
(
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    PRIMARY KEY (Id)
) engine = InnoDB;


-- creating a table for storing the forms and questions within them

drop table if exists forms;
CREATE TABLE if not exists forms
(
    Id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress_status enum('in progress','completed') not NULL default 'in progress',
    PRIMARY KEY (Id)
) engine = InnoDB;


-- creating a table for storing the questions

CREATE TABLE if not exists questions
(
    Id INT NOT NULL,
    form_id INT NOT NULL,
    question_text VARCHAR(255) NOT NULL,
    likert BOOLEAN NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (form_id) REFERENCES forms(Id)
) engine = InnoDB;


-- table to store submissions

CREATE TABLE submissions (
   id INT NOT NULL AUTO_INCREMENT,
   respondentID INT NOT NULL,
   respondentType VARCHAR(30) NOT NULL,
   submissionDate DATE,
   PRIMARY KEY (id),
   CONSTRAINT respondentCheck
       CHECK (
               (respondentType = 'reviewed' AND respondentID IN (SELECT id FROM users))
               OR
               (respondentType = 'reviewer' AND respondentID IN (SELECT id FROM reviewers))
           )
);


-- table to store answers

CREATE TABLE answers (
     id INT NOT NULL AUTO_INCREMENT,
     submissionID INT NOT NULL,
     questionID INT NOT NULL,
     answer_text VARCHAR(255),
     PRIMARY KEY (id),
     FOREIGN KEY (submissionID) REFERENCES submissions(id),
     FOREIGN KEY (questionID) REFERENCES questions(id)
);


-- -------------------------------------------------------------


-- creating a table for storing responders assigned to a form
-- the responders can be stored in a list in the form table but this will make it difficult to query the responders
-- this table will make it easier to query the responders assigned to a form

CREATE TABLE if not exists form_responders
(
    Id INT NOT NULL AUTO_INCREMENT,
    form_id VARCHAR(45) NOT NULL,
    responder_id INT NOT NULL,
    FOREIGN KEY (form_id) REFERENCES forms(Id),
    FOREIGN KEY (responder_id) REFERENCES users(id),
    PRIMARY KEY (Id)
) engine = InnoDB;


CREATE TABLE  IF NOT EXISTs job_categories
(
    Id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
) engine = InnoDB;






