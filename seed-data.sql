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

insert into ostp.book (isbn, name, price, cover) values ('9787-212-222-333', '数据结构&算法分析', 9999, 'images/adt.jpg');

insert into course_open (course, year, semester, book, teacher) values ('G101010', 2019, 3, '9787-212-222-333', '123458')

insert into second_hand_publish(person,book,price,exchange,status) values('201806060231','9787-212-222-423',12,0,1)
insert into second_hand_publish(person,book,price,exchange,status) values('201806061201','9787-212-222-423',12,1,1)