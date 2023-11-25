drop table if exists roles;
drop table if exists users_roles;
drop table if exists users;
drop table if exists form_requests;


drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;

use legalandgeneral;

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
    password varchar(255) not null
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




