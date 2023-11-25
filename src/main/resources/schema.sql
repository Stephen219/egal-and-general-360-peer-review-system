drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;

drop table if exists users;
create table if not exists users (
    id int not null auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null
)engine=InnoDB;


