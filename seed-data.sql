# noinspection SqlWithoutWhereForFile

delete
from student;

delete
from clazz;

delete
from major;

delete
from teacher;

delete
from college;

insert into college (name)
values ('计算机科学与技术学院');
insert into college (name)
values ('信息工程学院');

insert into major (name, college)
values ('软件工程', (select id from college where college.name = '计算机科学与技术学院'));

insert into major (name, college)
values ('计算机科学与技术', (select id from college where college.name = '计算机科学与技术学院'));

insert into clazz (name, major)
values ('移动应用开发方向1802', (select id from major where major.name = '软件工程'));

insert into clazz (name, major)
values ('移动应用开发方向1801', (select id from major where major.name = '软件工程'));

insert into student (id, name, clazz, password, balance, email)
values ('201806061219', '王程飞', (select id from clazz where clazz.name = '移动应用开发方向1802'), '123456', 999999,
        '1533144693@qq.com');

insert into student (id, name, clazz, password, balance, email)
values ('201806061108', '胡皓睿', (select id from clazz where clazz.name = '移动应用开发方向1802'), '123456', 999999,
        'i@huhaorui.com');

insert into student (id, name, clazz, password, balance, email)
values ('201806061201', '陈昊天', (select id from clazz where clazz.name = '移动应用开发方向1801'), '123456', 999999,
        '1542462994@outlook.com');

insert into student (id, name, clazz, password, balance, email)
values ('201806060231', '周科宇', (select id from clazz where clazz.name = '移动应用开发方向1801'), '123456', 999999,
        '201806060231@zjut.edu.cn');

insert into teacher (id, name, college, password, email)
values ('123456', '韩珊珊', (select id from college where college.name = '计算机科学与技术学院'), '123456', 'hss@zjut.ecu.cn');

insert into teacher (id, name, college, password, email)
values ('123457', '王春平', (select id from college where college.name = '计算机科学与技术学院'), '123456', 'wcp@zjut.ecu.cn');

insert into teacher (id, name, college, password, email)
values ('123458', '王松', (select id from college where college.name = '计算机科学与技术学院'), '123456', 'ws@zjut.ecu.cn');

insert into teacher (id, name, college, password, email)
values ('123459', '潘清', (select id from college where college.name = '信息工程学院'), '123456', 'pq@zjut.ecu.cn');

insert into ostp.book (isbn, name, price, cover)
values ('9787-212-222-333', '数据结构&算法分析', 9999, 'images/adt.jpg');
insert into ostp.book (isbn, name, price, cover)
values ('9787-212-233-344', 'JavaEE教程', 12999, 'images/javaee.jpg');
insert into ostp.book (isbn, name, price, cover)
values ('9787-213-243-432', '软件工程导论', 12999, 'images/se.jpg');
insert into ostp.book (isbn, name, price, cover)
values ('9787-645-424-231', '谭某的垃圾C++', 12999, 'images/cpp-t.jpg');



select id
from major
where name = '软件工程'
limit 1
into @SE;
select id
from major
where name = '计算机科学与技术'
limit 1
into @CS;

select id
from teacher
where name = '韩珊珊'
limit 1
into @HSS;
select id
from teacher
where name = '王春平'
limit 1
into @WCP;
select id
from teacher
where name = '王松'
limit 1
into @WS;
select id
from teacher
where name = '潘清'
limit 1
into @PQ;

insert into course (id, major, name)
values ('G101010', @SE, '软件工程');
insert into course (id, major, name)
values ('G101011', @SE, '数据结构与算法分析');
insert into course (id, major, name)
values ('G101012', @SE, 'JAVAEE开发');
insert into course (id, major, name)
values ('G101020', @CS, '软件工程');
insert into course (id, major, name)
values ('G101021', @CS, '数据结构与算法分析');
insert into course (id, major, name)
values ('G101022', @CS, 'JAVAEE开发');

insert into course_open (course, year, semester, book, teacher)
values ('G101012', 2019, 3, '9787-213-243-432', @HSS);
insert into course_open (course, year, semester, book, teacher)
values ('G101022', 2019, 3, '9787-213-243-432', @HSS);
insert into course_open (course, year, semester, book, teacher)
values ('G101012', 2020, 3, '9787-213-243-432', @HSS);
insert into course_open (course, year, semester, book, teacher)
values ('G101022', 2020, 3, '9787-213-243-432', @HSS);

insert into course_open (course, year, semester, book, teacher)
values ('G101012', 2020, 6, '9787-212-222-333', @WS);
insert into course_open (course, year, semester, book, teacher)
values ('G101010', 2019, 3, '9787-212-222-333', '123458');


insert into second_hand_publish(id, person, book, price, exchange, status)
values ('1234567', '201806060231', '9787-212-222-423', 12, 0, 1);
insert into second_hand_publish(id, person, book, price, exchange, status)
values ('1234567', '201806061201', '9787-212-222-423', 12, 1, 1);

insert into ostp.second_hand_find (id, person, book, price, exchange, status)
values ('af1e61d093eb44cc9fd96e81f0aeef05', '201806061108', '9787-212-222-333', 55, 0, 1);