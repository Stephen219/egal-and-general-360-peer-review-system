use legalandgeneral;

drop table if exists users_roles;
drop table if exists form_requests;
drop table if exists self_assessment;
drop table if exists reviewers;
drop table if exists answers;
drop table if exists domains;


drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;
drop table if exists answers;

drop table if exists roles;
drop table if exists users_roles;
drop table if exists form_requests;
drop table if exists self_assessment;
drop table if exists reviewers;

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
drop table if exists roles;
CREATE TABLE if not exists roles
(
    role_id int(11)     NOT NULL AUTO_INCREMENT primary key,
    name    varchar(45) NOT NULL
) engine = InnoDB;



drop table if exists users;
create table if not exists users (
    id int not null auto_increment primary key,
    username varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role_id int not null,
    enabled boolean not null default true,
    foreign key (role_id) references roles(role_id)



)engine=InnoDB;


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
    form_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
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





INSERT INTO 360forms (Id, username, created_at) VALUES
                                                                          ('form_2', 'henry', NOW()),
                                                                          ('form_3', 'henry', NOW()),
                                                                          ('form_4', 'henry', NOW()),
                                                                          ('form_5', 'henry', NOW()),
                                                                          ('form_6', 'henry', NOW()),
                                                                          ('form_7', 'henry', NOW()),
                                                                          ('form_8', 'henry', NOW()),
                                                                          ('form_9', 'henry', NOW()),
                                                                          ('form_1', 'henry', NOW()),
                                                                          ('form_10', 'henry', NOW()),
                                                                          ('form_11', 'henry', NOW()),
                                                                          ('form_12', 'henry', NOW()),
                                                                          ('form_13', 'henry', NOW()),
                                                                          ('form_14', 'henry', NOW()),
                                                                          ('form_15', 'henry', NOW()),
                                                                          ('form_16', 'henry', NOW()),
                                                                          ('form_17', 'henry', NOW()),
                                                                          ('form_18', 'henry', NOW()),
                                                                          ('form_19', 'henry', NOW()),
                                                                          ('form_20', 'henry', NOW()),
                                                                          ('form_21', 'henry', NOW()),
                                                                          ('form_22', 'henry', NOW()),
                                                                          ('form_23', 'henry', NOW());

INSERT into 360forms (Id, username) VALUES ('form1', 'Henry');
INSERT into 360forms (Id, username) VALUES ('form2', 'tom');
INSERT into 360forms (Id, username, created_at) VALUES ('form17', 'jerry', '2020-10-10 10:10:10');
INSERT into 360forms (Id, username, created_at) VALUES ('form3', 'kendi', '2021-10-10 10:10:10');

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

create table if not exists domains
(
    id int not null auto_increment primary key,
    domain_name varchar(255) not null,
    enabled boolean not null default true);



drop table if exists activation_tokens;




CREATE TABLE activation_tokens (
   token_id SERIAL PRIMARY KEY,
   email varchar(255) not null ,
   token VARCHAR(255) NOT NULL UNIQUE ,
   expiry TIMESTAMP,
   is_used BOOLEAN DEFAULT FALSE,
   type VARCHAR(255) NOT NULL  ,
    FOREIGN KEY (email) REFERENCES users (email)
);





-- create a procedure to insert a new user
-- 0nly the admininstrator can create a new user
-- the admininstrator will be the only one with the role of admin





