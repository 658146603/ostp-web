package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Book
import top.ostp.web.model.Course
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.Teacher

@Mapper
@Repository
interface CourseOpenMapper {

    @Insert("insert into course_open (course, year, semester, book, teacher) values (#{course.id}, #{year}, #{semester}, #{book.isbn}, #{teacher.id})")
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

    @Select("select * from course_open where year = #{year} and semester = #{semester}")
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
    fun selectByYearSemester(@Param("year") year: Int, @Param("semester") semester: Int): List<CourseOpen>

    @Select("select * from course_open where teacher = #{teacher.id}")
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
    fun selectByTeacher(@Param("teacher") teacher: Teacher): List<CourseOpen>

    @Select("select * from course_open where teacher = #{teacher.id} and year = #{year} and semester = #{semester}")
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
    fun selectByTeacherAndYearSemester(
        @Param("teacher") teacher: Teacher,
        @Param("year") year: Int,
        @Param("semester") semester: Int
    ): List<CourseOpen>
}