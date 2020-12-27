# select course_open by book and student
select course_open.* from (select * from course_open where book = '9787-212-222-333') course_open
    left join course on course_open.course = course.id
    left join major on course.major = major.id
    left join clazz on major.id = clazz.major
    left join student on clazz.id = student.clazz
    where student.id = '201806061201';

# select student_book order by book and student
select * from student_book_order where student = '201806061201' and book = '201806061201';

# select book by name and course
select book.* from (select * from book where book.name like '%C%') book
    left join course_open on book.isbn = course_open.book
    left join course on course_open.course = course.id
    where course.name like '%软%';

# select book by name and course and student
select distinct book.* from (select * from book where book.name like '%%') book
    left join course_open on book.isbn = course_open.book
    left join course on course_open.course = course.id
    left join major on course.major = major.id
where course.name like '%%' and major in (
    select major.id from (select * from student where student.id = '201806061201') student
        left join clazz on student.clazz = clazz.id
        left join major on clazz.major = major.id
);

# select book by name, course and teacher
select distinct book.* from (select * from book where book.name like '%%') book
    left join course_open on book.isbn = course_open.book
    left join course on course_open.course = course.id
where course.name like '%%' and course_open.teacher = '123458';


# select book_publish @buy @status:0 by name and except studentId
select second_hand_publish.* from second_hand_publish
    left join (select * from book where book.name like '%%') book on book.isbn = second_hand_publish.book
    where second_hand_publish.exchange = 0
        and second_hand_publish.status = 0
        and second_hand_publish.person != '201806061201';

update second_hand_publish set status = 0 where status != 0;
update second_hand_find set status = 0 where status != 0;

# select college fetches
select college.*,
    (select count(*) from major where major.college = college.id) majorCount,
    (select count(*) from teacher where teacher.college = college.id) teacherCount,
    (select count(*) from major join clazz on clazz.major = major.id join student on clazz.id = student.clazz where major.college = college.id) studentCount
    from college;

# select major by collegeId fetches
select major.*,
    (select count(*) from clazz where clazz.major = major.id) classCount,
    (select count(*) from clazz join student on clazz.id = student.clazz where clazz.major = major.id) studentCount
    from major where major.college = 7;

# select clazz fetches
select clazz.*,
    (select count(*) from student where student.clazz = clazz.id) studentCount
    from clazz where clazz.major = 14