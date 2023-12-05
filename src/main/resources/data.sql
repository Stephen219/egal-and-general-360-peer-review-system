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
                                               ('Experimenting to learn what works and pivoting when it doesn''t.', 'SecondCategory'),
                                               ('Accepting and building on others'' ideas and opinions to achieve the best possible outcome.', 'SecondCategory'),
                                               ('Working for the good of the collective rather than your own priorities.', 'SecondCategory'),
                                               ('Promoting and discussing how our purpose connects to the greater customer outcomes.', 'SecondCategory');

-- Insert questions for the third category
INSERT INTO questions (question, category) VALUES
                                               ('Actively asking for feedback and receiving it with curiosity.', 'ThirdCategory'),
                                               ('Listening openly to really understand.', 'ThirdCategory'),
                                               ('Taking the time to get to know others.', 'ThirdCategory'),
                                               ('Having the courage to challenge constructively and respectfully.', 'ThirdCategory');

-- Insert questions for the fourth category
INSERT INTO questions (question, category) VALUES
                                               ('Demonstrating high levels of self-awareness.', 'FourthCategory'),
                                               ('Creating an environment of trust where you trust others and earn others'' trust in return.', 'FourthCategory'),
                                               ('Empowering others to get on with their work and helping clear obstacles out of the way.', 'FourthCategory'),
                                               ('Behaving consistently across different situations.', 'FourthCategory');

-- Insert questions for the fifth category
INSERT INTO questions (question, category) VALUES
                                               ('Ruthlessly prioritizing and communicating it clearly.', 'FifthCategory'),
                                               ('Talking openly about your mistakes to help others learn.', 'FifthCategory'),
                                               ('Creating a climate where people feel safe and are encouraged to speak up.', 'FifthCategory'),
                                               ('Anticipating and responding to change at pace.', 'FifthCategory'),
                                               ('Driving commercial value that focuses on our customers and shareholders.', 'FifthCategory');

-- Insert questions for the sixth category
INSERT INTO questions (question, category) VALUES
                                               ('Driving enterprise-wide success.', 'SixthCategory'),
                                               ('Driving outcomes that start with our customers.', 'SixthCategory'),
                                               ('Pushing boundaries to create opportunities for future success.', 'SixthCategory'),
                                               ('Thinking about the future and changes that are needed.', 'SixthCategory');

insert into questions (question, category) VALUEs('What do you consider to be your superpower? :', 'textarea');
insert into questions (question, category) VALUEs('What is the one thing you could do to improve your impact? :', 'textarea');