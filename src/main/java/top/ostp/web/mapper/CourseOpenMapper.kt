package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Book
import top.ostp.web.model.Course
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.Teacher
import top.ostp.web.model.complex.SearchParams2

@Mapper
@Repository
interface CourseOpenMapper {

    @Insert("insert into course_open (course, year, semester, book, teacher, received) values (#{course.id}, #{year}, #{semester}, #{book.isbn}, #{teacher.id}, 0)")
    fun insert(courseOpen: CourseOpen): Int

    @Delete("delete from course_open where (course, year, semester) = (#{course.id}, #{year}, #{semester})")
    fun delete(courseOpen: CourseOpen): Int

    @Update("update course_open set teacher = #{teacher.id}, book = #{book.isbn} where (course, year, semester) = (#{course.id}, #{year}, #{semester})")
    fun update(courseOpen: CourseOpen): Int

    @Select("select * from course_open")
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    fun selectAll(): List<CourseOpen>

    @Select("select * from course_open where course = #{course.id}")
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    fun selectByCourse(@Param("course") course: Course): List<CourseOpen>

    @Select("select * from course_open where book = #{book.isbn}")
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    fun selectByBook(@Param("book") book: Book): List<CourseOpen>


    @Update("update course_open set received = 1 where id = #{id}")
    fun requestBook(id: Int): Int

    @Select(
        """
        select course_open.* from (select * from course_open where book = #{isbn}) course_open
            left join course on course_open.course = course.id
            left join major on course.major = major.id
            left join clazz on major.id = clazz.major
            left join student on clazz.id = student.clazz
            where student.id = #{studentId}
    """
    )
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    fun selectByBookAndStudent(@Param("isbn") isbn: String, @Param("studentId") studentId: String): List<CourseOpen>

    @Select("""
        select * from course_open where book = #{isbn} and teacher = #{teacherId}
    """)
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    fun selectByBookAndTeacher(@Param("isbn") isbn: String, @Param("teacherId") teacherId: String): List<CourseOpen>

    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    @Select("""
select *
from course_open
where year = #{year}
  and semester = #{semester}
  and teacher in (select teacher.id from teacher where college = #{college})
""")
    fun selectByCollegeAndYearSemester(@Param("college") college: Int, @Param("year") year: Int, @Param("semester") semester: Int): List<CourseOpen>

    @Select("""
    select * from (select * from course_open where course_open.year = #{year} and course_open.semester = #{semester} and book = #{isbn}) course_open
        join course on course_open.course = course.id
        join major on major.id = course.major
        join college on college.id = major.college
        where college.id in (select admin.college from admin where admin.id = #{personId})
    """)
    @Results(
        value = [
            Result(
                property = "course", column = "course",
                one = One(select = "top.ostp.web.mapper.CourseMapper.selectById")
            ),
            Result(
                property = "book", column = "book",
                one = One(select = "top.ostp.web.mapper.BookMapper.selectByISBN")
            ),
            Result(
                property = "teacher", column = "teacher",
                one = One(select = "top.ostp.web.mapper.TeacherMapper.selectById")
            )
        ]
    )
    @ResultType(CourseOpen::class)
    fun selectByBookAndCollegeAdmin(params2: SearchParams2): List<CourseOpen>
}