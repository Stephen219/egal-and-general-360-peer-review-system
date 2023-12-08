use legalandgeneral;
insert into users (username, password, enabled)
values ('admin', '$2a$12$zKMHlbOC7UXOLCdq5ZibC.ANCzcxgpoGbode97Dc2Fi1zakG2fP6O', true);
insert into users (username, password, enabled)
values ('user', '$2a$12$J1s9zHrJiu9VKV8J7FFXJ.U0ArVGE.9h3R94.a0OGx7a7ast0bcYi', true);

-- role inserts
insert into roles (role_id, name)
values (1, 'ADMIN');
insert into roles (role_id, name)
values (2, 'USER');

-- assigning users roles
insert into users_roles (username, role_id)
values ('admin', 1);
insert into users_roles (username, role_id)
values ('user', 2);

INSERT INTO questions (question, category) VALUES
                                               ('Building meaningful relationships across teams and projects.', 'Collaborative'),
                                               ('Sharing your experiences openly so that people can learn.', 'Collaborative'),
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

insert into questions (question, category) VALUEs('What do you consider to be your superpower? :', 'textarea');
insert into questions (question, category) VALUEs('What is the one thing you could do to improve your impact? :', 'textarea');




insert into reviewers (form_id, email) VALUES ('form1', 'ubbur@hcbdhchhc.com');
insert into reviewers (form_id, email) VALUES ('form1', 'hjdjjfjfdjfdjdfjjkd@gmailcom');

insert into reviewers (form_id, email) VALUES ('form1', 'abushvin@gmail.com');
insert into reviewers (form_id, email) VALUES ('form1', 'hjdjjfjiksasfdjfdjdfjjkd@gmailcom');
insert into reviewers (form_id, email) VALUES ('form1', 'hjdjjfsakuasukjfdjfdjdfjjkd@gmailcom');
insert into reviewers (form_id, email) VALUES ('form1', 'hjdjjfsxuiuisxjfdjfdjdfjjkd@gmailcom');