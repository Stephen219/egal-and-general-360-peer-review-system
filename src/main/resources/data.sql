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

-- form inserts
insert into forms (Id, created_at, progress_status)
values (1, '2020-01-01', 'in progress');
-- question inserts
insert into questions (Id, form_id, question_text, likert)
values (1, 1, 'Building meaningful relationships across teams and projects.', true);

insert into questions (Id, form_id, question_text, likert)
values (2, 1, 'Sharing your experiences openly so that people can learn.', true);

insert into questions (Id, form_id, question_text, likert)
values (3, 1, 'Actively seeking out opportunities to support others in work.', true);

insert into questions (Id, form_id, question_text, likert)
values (4, 1, 'As being open and approachable', true);

insert into questions (Id, form_id, question_text, likert)
values (5, 1, 'Experimenting to learn what works and pivoting when it doesnt.', true);

insert into questions (Id, form_id, question_text, likert)
values (6, 1, 'Accepting and building on others'' ideas and opinions to achieve the best possible outcome.', true);

insert into questions (Id, form_id, question_text, likert)
values (7, 1, 'Working for the good of the collective rather thank your own priorities.', true);

insert into questions (Id, form_id, question_text, likert)
values (8, 1, 'Promoting and discussing how our purpose connects to the greater customer outcomes.', true);

insert into questions (Id, form_id, question_text, likert)
values (9, 1, 'Actively asking for feedback and receiving it with curiosity.', true);

insert into questions (Id, form_id, question_text, likert)
values (10, 1, 'Listening openly to really understand.', true);

insert into questions (Id, form_id, question_text, likert)
values (11, 1, 'Taking the time to get to know others.', true);

insert into questions (Id, form_id, question_text, likert)
values (12, 1, 'Having the courage to challenge constructively and respectfully.', true);

insert into questions (Id, form_id, question_text, likert)
values (13, 1, 'Demonstrating high levels of self-awareness.', true);

insert into questions (Id, form_id, question_text, likert)
values (14, 1, 'Creating an environment of trust where you trust others'' and earn others'' trust in return.', true);

insert into questions (Id, form_id, question_text, likert)
values (15, 1, 'Empowering others to get on with their work and helping the clear obstacles out of the way.', true);

insert into questions (Id, form_id, question_text, likert)
values (16, 1, 'Behaving consistently across different situations.', true);

insert into questions (Id, form_id, question_text, likert)
values (17, 1, 'Ruthlessly prioritising and communicating it clearly.', true);

insert into questions (Id, form_id, question_text, likert)
values (18, 1, 'Talking openly about your mistakes to help others learn.', true);

insert into questions (Id, form_id, question_text, likert)
values (19, 1, 'Creating a climate where people fell safe and are encouraged to speak up.', true);

insert into questions (Id, form_id, question_text, likert)
values (20, 1, 'Anticipating and responding to change at pace.', true);

insert into questions (Id, form_id, question_text, likert)
values (21, 1, 'Driving commercial value that focus on our customers and shareholders.', true);

insert into questions (Id, form_id, question_text, likert)
values (22, 1, 'Driving enterprise-wide success.', true);

insert into questions (Id, form_id, question_text, likert)
values (23, 1, 'Driving outcomes that start with our customers.', true);

insert into questions (Id, form_id, question_text, likert)
values (24, 1, 'Pushing boundaries to create opportunities for future success.', true);

insert into questions (Id, form_id, question_text, likert)
values (25, 1, 'Thinking about the future and changes that are needed', true);

insert into questions (Id, form_id, question_text, likert)
values (26, 1, 'What do you consider to be your superpower?', false);

insert into questions (Id, form_id, question_text, likert)
values (27, 1, 'What is the one thing you could do to improve your impact?', false);
