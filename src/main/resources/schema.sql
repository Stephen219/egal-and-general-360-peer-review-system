use legalandgeneral;
drop table if exists roles;
drop table if exists users_roles;
drop table if exists form_requests;
drop table if exists self_assessment;
drop table if exists reviewers;
drop table if exists answers;


drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;


 -- create a sequence for generating the answer  id


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
    form_id VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    hasFilledForm BOOLEAN NOT NULL DEFAULT FALSE,
    relationship VARCHAR(45)  DEFAULT null,
    PRIMARY KEY (Id)
) engine = InnoDB;









drop table if exists 360forms;
CREATE TABLE if not exists 360forms
(
    Id VARCHAR(45) NOT NULL,
    username VARCHAR(45) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress_status enum('in progress','completed') not NULL default 'in progress',
    PRIMARY KEY (Id)
) engine = InnoDB;

INSERT into 360forms (Id, username) VALUES ('form1', 'user');
INSERT into 360forms (Id, username) VALUES ('form2', 'user');

CREATE TABLE if not exists questions
(
    Id INT NOT NULL AUTO_INCREMENT,
    question VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
) engine = InnoDB;




create table if not exists answers
(
    Id int not null auto_increment primary key,
    form_id varchar(45) not null,
    question_id int not null,
    responder varchar(255) not null,
    answer varchar(255) not null,
    answer_time timestamp default current_timestamp,
    foreign key (form_id) references 360forms(Id),
    foreign key (question_id) references questions(Id)

)engine=InnoDB;



