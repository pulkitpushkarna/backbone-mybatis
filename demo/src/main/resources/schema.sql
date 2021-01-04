DROP TABLE IF EXISTS user ;
DROP TABLE IF EXISTS faculty;
DROP TABLE IF EXISTS persistent_logins;
DROP TABLE IF EXISTS student_class;
DROP TABLE IF EXISTS student;
create table user
(
    id               int auto_increment
        primary key,
    user_name        varchar(255) not null,
    password         varchar(255) not null,
    confirm_password varchar(255) not null,
    roles            varchar(255) not null,
    active           tinyint(1)   not null

);

create table faculty
(
    faculty_id   int auto_increment
        primary key,
    name         varchar(255) not null,
    class_name   varchar(255) null,
    faculty_code varchar(255) null
);

create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) not null
        primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);

create table student
(
    student_id int auto_increment
        primary key,
    roll_no    varchar(225) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    class_name varchar(255) not null,
    address    varchar(255) not null,
    city       varchar(255) not null,
    deleted    tinyint(1)   not null
);

create table student_class
(
    id           int auto_increment
        primary key,
    class_code   varchar(255) not null,
    class_name   varchar(255) not null,
    faculty_name varchar(255) not null
);

