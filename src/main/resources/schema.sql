drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;

create table if not exists users (
    id int not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);


