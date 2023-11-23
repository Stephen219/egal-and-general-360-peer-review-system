drop schema if exists legalandgeneral;
CREATE SCHEMA legalandgeneral;
use legalandgeneral;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT unique_username UNIQUE (username),
    CONSTRAINT unique_email UNIQUE (email)
);


CREATE TABLE forms (
   form_id INT AUTO_INCREMENT PRIMARY KEY,
   user_id INT, -
   form_name VARCHAR(100) NOT NULL,
   form_status VARCHAR(20) DEFAULT 'in progress',
   approval_status BOOLEAN DEFAULT FALSE,
   CONSTRAINT fk_forms_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE form_requests (
    form_request_id INT AUTO_INCREMENT PRIMARY KEY,
   user_id INT,
   form_id INT,
   form_name VARCHAR(100) NOT NULL,
   request_status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
   CONSTRAINT fk_form_requests_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
   CONSTRAINT fk_form_requests_form_id FOREIGN KEY (form_id) REFERENCES forms(form_id)
);

