insert into user(id, username, password, active)
values (1000, 'WilFerraciolli@wiltech.com', '{bcrypt}$2a$10$2WacIN6u7bxhQOkx9gxPAOaTZjab0GCzoCSdJF7HU5ajf5CC4hgga', 1);
insert into User_Roles(user_id, role)
values (1000, 'ROLE_ADMIN');
insert into User_Roles(user_id, role)
values (1000, 'ROLE_HR_ADMIN');
insert into User_Roles(user_id, role)
values (1000, 'ROLE_USER');

insert into User(id, username, password, active)
values (1001, 'George@wiltech.com', '{bcrypt}$2a$10$2WacIN6u7bxhQOkx9gxPAOaTZjab0GCzoCSdJF7HU5ajf5CC4hgga', 1);
insert into User_Roles(user_id, role)
values (1001, 'ROLE_USER');
insert into User_Roles(user_id, role)
values (1001, 'ROLE_ADMIN');

insert into User(id, username, password, active)
values (1002, 'Maria@wiltech.com', '{bcrypt}$2a$10$2WacIN6u7bxhQOkx9gxPAOaTZjab0GCzoCSdJF7HU5ajf5CC4hgga', 1);
insert into User_Roles(user_id, role)
values (1002, 'ROLE_USER');

insert into person(id, user_Id, first_name, last_name, email, gender, phone_Number, date_of_birth, marital_status, number_of_dependants)
values (2000, 1000, 'Wiliam', 'Ferraciolli', 'WilFerraciolli@wiltech.com', 'MALE', '+44 7540595289', '1985-02-16', 'MARRIED', 1);
insert into person(id, user_Id, first_name, last_name, email, gender, phone_Number, date_of_birth, marital_status, number_of_dependants)
values (2001, 1001, 'George', 'Ferraciolli', 'George@wiltech.com', 'MALE', '+44 7540595289', '2015-07-13', 'SINGLE', 0);
insert into person(id, user_Id, first_name, last_name, email, gender, phone_Number, date_of_birth, marital_status, number_of_dependants)
values (2002, 1002, 'Maria', 'Georgiou', 'Maria@wiltech.com', 'FEMALE', '+44 7540595289', '2015-07-13', 'SINGLE', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3000, 2000, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3001, 2000, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3009, 2000, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3008, 2000, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-03-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3003, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3004, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3005, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3006, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (3010, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30011, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30092, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30083, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30034, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30045, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30056, 2001, 'WORK', 'Sample To do', ' Description of sample to do', '2020-01-01 12:00:00', 'NEW', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30067, 2001, 'PERSONAL', 'Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);

insert into todo(id, person_id, type_id, name, description, created_date_time, state_id, enabled)
values (30069, 2001, 'PERSONAL', 'Last Sample To do 2', ' Description of sample to do 2', '2020-01-31 19:00:00', 'STARTED', 1);
