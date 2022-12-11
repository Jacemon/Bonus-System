drop database if exists `bonus_system`;
create database `bonus_system`;

use `bonus_system`;

drop table if exists `user`;
drop table if exists `role`;
drop table if exists `task`;
drop table if exists `bonus`;
drop table if exists `employee`;

create table `bonus`
(
    id     int auto_increment
        primary key,
    type   enum ('POINTS', 'MONEY', 'PERCENT') not null,
    amount float                               not null
);

create table `employee`
(
    id         int auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    salary     float       not null
);

create table `role`
(
    name             varchar(20)    not null
        primary key,
    access_level int default -1 not null
);
insert into `role` (name) values ('UNDEFINED');
insert into `role` (name, access_level) values ('ADMIN', 0);
insert into `role` (name, access_level) values ('COMMON', 4);

create table `task`
(
    id            int auto_increment
        primary key,
    description   text                                 not null,
    creation_time timestamp  default CURRENT_TIMESTAMP not null,
    is_completed  tinyint(1) default 0                 not null,
    is_paid       tinyint(1) default 0                 not null,
    bonus_id      int                                  not null,
    employee_id   int                                  null,
    constraint task_bonus_id_fk
        foreign key (bonus_id) references bonus (id)
            on delete cascade,
    constraint task_employee_id_fk
        foreign key (employee_id) references employee (id)
            on delete set null
);

create table `user`
(
    id            int auto_increment
        primary key,
    login         varchar(40) not null,
    password_hash blob        not null,
    role_name     varchar(20) not null,
    employee_id   int         null,
    constraint user_employee_pk
        unique (employee_id),
    constraint user_login_pk
        unique (login),
    constraint user_employee_id_fk
        foreign key (employee_id) references employee (id)
            on delete set null,
    constraint user_role_name_fk
        foreign key (role_name) references role (name)
);