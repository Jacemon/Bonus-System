drop database if exists `bonus_system`;
create database `bonus_system`;

use `bonus_system`;

drop table if exists `user`;
drop table if exists `role`;
drop table if exists `bonus`;
drop table if exists `employee`;
drop table if exists `task`;

create table `employee`
(
    id         int auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(20) not null
);

create table role
(
    name varchar(20) not null
        primary key
);

create table `task`
(
    id            int auto_increment
        primary key,
    description   text                                not null,
    creation_time timestamp default CURRENT_TIMESTAMP not null,
    status        enum ('new', 'taken', 'completed')  not null,
    employee_id   int                                 null,
    constraint task_employee_id_fk
        foreign key (employee_id) references employee (id)
);

create table `bonus`
(
    id      int auto_increment
        primary key,
    type    varchar(20) not null,
    amount  float       not null,
    task_id int         not null,
    constraint bonus_task_id_fk
        foreign key (task_id) references task (id)
            on delete cascade
);

create table `user`
(
    login         varchar(40) not null
        primary key,
    employee_id   int         null,
    password_hash blob        not null,
    role_name     varchar(20) null,
    constraint user_employee_id_fk
        foreign key (employee_id) references employee (id),
    constraint user_role_name_fk
        foreign key (role_name) references role (name)
            on delete set null
);