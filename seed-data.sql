# noinspection SqlWithoutWhereForFile

delete
from student;

delete
from class;

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

insert into class (name, major)
values ('移动应用开发方向1802', (select id from major where major.name = '软件工程'));

insert into class (name, major)
values ('移动应用开发方向1801', (select id from major where major.name = '软件工程'));

insert into student (id, name, class, password, balance, email)
values ('201806061219', '王程飞', (select id from class where class.name = '移动应用开发方向1802'), '123456', 999999,
        '1533144693@qq.com');

insert into student (id, name, class, password, balance, email)
values ('201806061201', '陈昊天', (select id from class where class.name = '移动应用开发方向1801'), '123456', 999999,
        '1542462994@outlook.com');

insert into student (id, name, class, password, balance, email)
values ('201806060231', '周科宇', (select id from class where class.name = '移动应用开发方向1801'), '123456', 999999,
        '201806060231@zjut.edu.cn');

insert into teacher (id, name, college, password, email)
values ('123456', '韩珊珊', (select id from college where college.name = '计算机科学与技术学院'), '123456', 'hss@zjut.ecu.cn');

insert into teacher (id, name, college, password, email)
values ('123457', '王春平', (select id from college where college.name = '计算机科学与技术学院'), '123456', 'wcp@zjut.ecu.cn');
