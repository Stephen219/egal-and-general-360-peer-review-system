use legalandgeneral;

insert into roles (role_id, name)
values (1, 'ROLE_ADMIN');
insert into roles (role_id, name)
values (2, 'ROLE_USER');

insert into users (username, email, password, enabled ,role_id)
values ('John','admin@gmail.com', '$2a$12$zKMHlbOC7UXOLCdq5ZibC.ANCzcxgpoGbode97Dc2Fi1zakG2fP6O', true,1);
insert into users (username,email, password, enabled, role_id)
values ('Henry','test@gmail.com', '$2a$12$J1s9zHrJiu9VKV8J7FFXJ.U0ArVGE.9h3R94.a0OGx7a7ast0bcYi', true, 2);
insert into activation_tokens (email, token, expiry, type) values ('test@gmail.com','valid_token', '2024-10-10 10:10:10', 'reset');


-- role inserts

-- assigning users roles
insert into users_roles (username, role_id)
values ('admin', 1);
insert into users_roles (username, role_id)
values ('user', 2);

INSERT INTO questions (question, category) VALUES
                                               ('Building meaningful relationships across teams and projects.', 'Collaborative'),
                                               ('Sharing experiences openly so that people can learn.', 'Collaborative'),
                                               ('Actively seeking out opportunities to support others in work.', 'Collaborative'),
                                               ('Being open and approachable.', 'Collaborative');

-- Insert questions for the second category
INSERT INTO questions (question, category) VALUES
                                               ('Experimenting to learn what works and pivoting when it doesn''t.', 'Purposeful'),
                                               ('Accepting and building on others'' ideas and opinions to achieve the best possible outcome.', 'Purposeful'),
                                               ('Working for the good of the collective rather than your own priorities.', 'Purposeful'),
                                               ('Promoting and discussing how our purpose connects to the greater customer outcomes.', 'Purposeful');

-- Insert questions for the third category
INSERT INTO questions (question, category) VALUES
                                               ('Actively asking for feedback and receiving it with curiosity.', 'Straight-forward'),
                                               ('Listening openly to really understand.', 'Straight-forward'),
                                               ('Taking the time to get to know others.', 'Straight-forward'),
                                               ('Having the courage to challenge constructively and respectfully.', 'Straight-forward');

-- Insert questions for the fourth category
INSERT INTO questions (question, category) VALUES
                                               ('Demonstrating high levels of self-awareness.', 'Authenticity'),
                                               ('Creating an environment of trust where you trust others and earn others'' trust in return.', 'Authenticity'),
                                               ('Empowering others to get on with their work and helping clear obstacles out of the way.', 'Authenticity'),
                                               ('Behaving consistently across different situations.', 'Authenticity');

-- Insert questions for the fifth category
INSERT INTO questions (question, category) VALUES
                                               ('Ruthlessly prioritizing and communicating it clearly.', 'Agile'),
                                               ('Talking openly about your mistakes to help others learn.', 'Agile'),
                                               ('Creating a climate where people feel safe and are encouraged to speak up.', 'Agile'),
                                               ('Anticipating and responding to change at pace.', 'Agile'),
                                               ('Driving commercial value that focuses on our customers and shareholders.', 'Agile');

-- Insert questions for the sixth category
INSERT INTO questions (question, category) VALUES
                                               ('Driving enterprise-wide success.', 'Ambitious'),
                                               ('Driving outcomes that start with our customers.', 'Ambitious'),
                                               ('Pushing boundaries to create opportunities for future success.', 'Ambitious'),
                                               ('Thinking about the future and changes that are needed.', 'Ambitious');

insert into questions (question, category) VALUEs('What do you consider to be your/this person\'s superpower? :', 'textarea');
insert into questions (question, category) VALUEs('What is one thing you could do to improve your impact, or what is one thing they could do to improve their impact :', 'textarea');




insert into reviewers (form_id, email) VALUES ('form1', 'test4@gmail.com');
insert into reviewers (form_id, email) VALUES ('form1', 'htest4@gmailcom');

insert into reviewers (form_id, email) VALUES ('form1', 'abushvin@gmail.com');
insert into reviewers (form_id, email) VALUES ('form1', 'test1@gmailcom');
insert into reviewers (form_id, email) VALUES ('form1', 'test2@gmailcom');
insert into reviewers (form_id, email) VALUES ('form1', 'test3@gmailcom');
insert into reviewers (form_id, email) VALUES ('form1', 'pass@gmail.com');

insert into domains (domain_name) VALUES ('landg.com');
insert into domains (domain_name) VALUES ('gmail.com');
insert into domains (domain_name) VALUES ('landghomefinance.com');
insert into domains (domain_name) VALUES ('theidol.com');
insert into domains (domain_name) VALUES ('lgim.com');
insert into domains (domain_name) VALUES ('lgsurvey.co.uk');
insert into domains (domain_name) VALUES ('gmail.com');
insert into domains (domain_name) VALUES ('cardiff.ac.uk');


insert into activation_tokens (email, token, expiry, type) values ('admin@gmail.com','<script>alert("hello")</script>', '2024-10-10 10:10:10', 'reset');

INSERT INTO answers (question_id, form_id, answer, responder)
VALUES
    (1, 'form1', '1', 'henry@w'),
    (2, 'form1', '2', 'henry@w'),
    (3, 'form1', '3', 'henry@w'),
    (4, 'form1', '4', 'henry@w'),
    (5, 'form1', '5', 'henry@w'),
    (6, 'form1', '1', 'henry@w'),
    (7, 'form1', '2', 'henry@w'),
    (8, 'form1', '3', 'henry@w'),
    (9, 'form1', '4', 'henry@w'),
    (10, 'form1', '5', 'henry@w'),
    (11, 'form1', '1', 'henry@w'),
    (12, 'form1', '2', 'henry@w'),
    (13, 'form1', '3', 'henry@w'),
    (14, 'form1', '4', 'henry@w'),
    (15, 'form1', '5', 'henry@w'),
    (16, 'form1', '1', 'henry@w'),
    (17, 'form1', '2', 'henry@w'),
    (18, 'form1', '3', 'henry@w'),
    (19, 'form1', '4', 'henry@w'),
    (20, 'form1', '5', 'henry@w'),
    (21, 'form1', '1', 'henry@w'),
    (22, 'form1', '2', 'henry@w'),
    (23, 'form1', '3', 'henry@w'),
    (24, 'form1', '4', 'henry@w'),
    (25, 'form1', '5', 'henry@w'),
    (26, 'form1', 'good', 'henry@w'),
    (27, 'form1', 'do more', 'henry@w');
INSERT INTO answers (question_id, form_id, answer, responder)
VALUES
    (1, 'form3', '1', 'bob@gmail.com'),
    (2, 'form3', '2', 'bob@gmail.com'),
    (3, 'form3', '3', 'bob@gmail.com'),
    (4, 'form3', '4', 'bob@gmail.com'),
    (5, 'form3', '5', 'bob@gmail.com'),
    (6, 'form3', '1', 'bob@gmail.com'),
    (7, 'form3', '2', 'bob@gmail.com'),
    (8, 'form3', '3', 'bob@gmail.com'),
    (9, 'form3', '4', 'bob@gmail.com'),
    (10, 'form3', '5', 'bob@gmail.com'),
    (11, 'form3', '1', 'bob@gmail.com'),
    (12, 'form3', '2', 'bob@gmail.com'),
    (13, 'form3', '3', 'bob@gmail.com'),
    (14, 'form3', '4', 'bob@gmail.com'),
    (15, 'form3', '5', 'bob@gmail.com'),
    (16, 'form3', '1', 'bob@gmail.com'),
    (17, 'form3', '2', 'bob@gmail.com'),
    (18, 'form3', '3', 'bob@gmail.com'),
    (19, 'form3', '4', 'bob@gmail.com'),
    (20, 'form3', '5', 'bob@gmail.com'),
    (21, 'form3', '1', 'bob@gmail.com'),
    (22, 'form3', '2', 'bob@gmail.com'),
    (23, 'form3', '3', 'bob@gmail.com'),
    (24, 'form3', '4', 'bob@gmail.com'),
    (25, 'form3', '5', 'bob@gmail.com'),
    (26, 'form3', '&&&&&***', 'bob@gmail.com'),
    (27, 'form3', 'this <>?D & is a test','bob@gmail.com')