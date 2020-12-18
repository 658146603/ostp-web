drop database ostp;
create database if not exists ostp character set utf8mb4 collate utf8mb4_bin;

use ostp;


create table college
(
    id   integer auto_increment not null primary key,
    name varchar(32)            not null
);

create table major
(
    id      integer auto_increment not null primary key,
    name    varchar(32)            not null,
    college integer                not null,
    foreign key (college) references college (id)
);

create table class
(
    id    integer auto_increment not null primary key,
    name  varchar(32)            not null,
    major integer                not null,
    foreign key (major) references major (id)
);


create table student
(
    id       varchar(32) not null primary key,
    name     varchar(32) not null,
    class    integer     not null,
    foreign key (class) references class (id),
    password varchar(64) not null,
    balance  decimal     not null,
    email    varchar(64) null
);

create table teacher
(
    id       varchar(32) not null primary key,
    name     varchar(32) not null,
    college  integer     not null,
    password varchar(64) not null,
    foreign key (college) references college (id),
    email    varchar(64) not null
);

create table book
(
    isbn  varchar(32)  not null primary key,
    name  varchar(64)  not null,
    price decimal      not null,
    cover varchar(128) not null # file path
);

create table course
(
    id    integer not null primary key,
    major integer not null,
    foreign key (major) references major (id),
    name  integer not null
);


create table course_open
(
    course  integer     not null,
    foreign key (course) references course (id),
    primary key (course),
    book    varchar(32) not null,
    foreign key (book) references book (isbn),
    teacher varchar(32) not null,
    foreign key (teacher) references teacher (id),
    class integer not null ,
    foreign key (class) references class(id)
);

create table second_hand_publish
(
    person   varchar(32) not null,
    foreign key (person) references student (id),
    book     varchar(32) not null,
    foreign key (book) references book (isbn),
    primary key (person, book),
    price    decimal     not null,
    exchange boolean     not null default false,
    status   integer     not null
);

create table second_hand_find
(
    person   varchar(32) not null,
    foreign key (person) references student (id),
    book     varchar(32) not null,
    foreign key (book) references book (isbn),
    primary key (person, book),
    price    decimal     not null,
    exchange boolean     not null default false,
    status   integer     not null
);