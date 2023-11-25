drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;
drop table if exists form_requests;
Create TABLE IF NOT EXISTS form_requests (
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_status VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (Id)
);

