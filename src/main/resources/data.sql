delete from users;
insert into users (id, username, password) values (1,'user3', 'password');
insert into users (id, username, password) values (2,'user4', 'password');

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
