drop table if exists roles;
drop table if exists users_roles;
drop table if exists form_requests;


drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;

use legalandgeneral;
# CREATE TABLE  IF NOT EXISTs job_categories
# (
#     Id INT NOT NULL AUTO_INCREMENT,
#     category_name VARCHAR(255) NOT NULL,
#     PRIMARY KEY (Id)
# ) engine = InnoDB;

Create TABLE IF NOT EXISTS form_requests (
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_status VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (Id)
);

drop table if exists users;
create table if not exists users (
    id int not null auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    category_id int not null default 1,
    enabled boolean not null default true
        -- FOREIGN KEY (category_id) REFERENCES job_categories(Id)
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




drop table if exists 360forms;
CREATE TABLE if not exists 360forms
(
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress_status enum('in progress','completed') not NULL default 'in progress',
    PRIMARY KEY (Id)
) engine = InnoDB;

-- creating a table for storing the questions
CREATE TABLE if not exists questions
(
    Id INT NOT NULL AUTO_INCREMENT,
    question VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
) engine = InnoDB;


-- creating a table for storing the answers
CREATE TABLE if not exists answers
(
    Id INT NOT NULL AUTO_INCREMENT,
    form_id INT NOT NULL,
    question_id INT NOT NULL,
    responder_id INT NOT NULL,

    answer VARCHAR(255) NOT NULL,
    answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (form_id) REFERENCES 360forms(Id),
    FOREIGN KEY (question_id) REFERENCES questions(Id),
    FOREIGN KEY (responder_id) REFERENCES users(id),

    PRIMARY KEY (Id)
) engine = InnoDB;



-- creating a table for storing responders assigned to a form
-- the responders can be stored in a list in the form table but this will make it difficult to query the responders
-- this table will make it easier to query the responders assigned to a form

CREATE TABLE if not exists form_responders
(
    Id INT NOT NULL AUTO_INCREMENT,
    form_id INT NOT NULL,
    responder_id INT NOT NULL,
    FOREIGN KEY (form_id) REFERENCES 360forms(Id),
    FOREIGN KEY (responder_id) REFERENCES users(id),
    PRIMARY KEY (Id)
) engine = InnoDB;


CREATE TABLE  IF NOT EXISTs job_categories
(
    Id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
) engine = InnoDB;






