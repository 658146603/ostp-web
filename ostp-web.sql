drop database ostp;
create database if not exists ostp character set utf8mb4 collate utf8mb4_bin;

use ostp;


create table college
(
    id   integer auto_increment not null primary key,
    name varchar(32)            not null unique
);

create table major
(
    id      integer auto_increment not null primary key,
    name    varchar(32)            not null,
    college integer                not null,
    year    integer                not null,
    unique index (name, college, year),
    foreign key (college) references college (id)
);

create table clazz
(
    id    integer auto_increment not null primary key,
    name  varchar(32)            not null,
    major integer                not null,
    unique index (name, major),
    foreign key (major) references major (id)
);


create table student
(
    id       varchar(32) not null primary key,
    name     varchar(32) not null,
    clazz    integer     not null,
    foreign key (clazz) references clazz (id),
    password varchar(64) not null,
    balance  integer     not null,
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
    price integer      not null,
    cover varchar(128) not null # file path
);

create table course
(
    id    varchar(32) not null primary key,
    major integer     not null,
    foreign key (major) references major (id),
    name  varchar(64) not null
);

# drop table if exists course_open;

create table course_open
(
    id       integer     not null auto_increment primary key,
    course   varchar(32) not null,
    foreign key (course) references course (id),
    year     integer     not null,
    semester integer     not null,
    unique index (course, year, semester),
    book     varchar(32) not null,
    foreign key (book) references book (isbn),
    teacher  varchar(32) not null,
    foreign key (teacher) references teacher (id)
);

create table second_hand_publish
(
    person   varchar(32) not null,
    foreign key (person) references student (id),
    book     varchar(32) not null,
    foreign key (book) references book (isbn),
    primary key (person, book),
    price    integer     not null,
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
    price    integer     not null,
    exchange boolean     not null default false,
    status   integer     not null
);