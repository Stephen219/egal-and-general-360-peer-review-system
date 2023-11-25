insert into users (username, password, enabled)
values ('admin', '$2a$12$zKMHlbOC7UXOLCdq5ZibC.ANCzcxgpoGbode97Dc2Fi1zakG2fP6O', true);
insert into users (username, password, enabled)
values ('user', '$2a$12$J1s9zHrJiu9VKV8J7FFXJ.U0ArVGE.9h3R94.a0OGx7a7ast0bcYi', true);


insert into roles (role_id, name)
values (1, 'ADMIN');
insert into roles (role_id, name)
values (2, 'USER');

insert into users_roles (username, role_id)
values ('admin', 1);
insert into users_roles (username, role_id)
values ('admin', 2);
insert into users_roles (username, role_id)
values ('user', 2);
