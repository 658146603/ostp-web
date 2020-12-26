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

    @Insert("insert into course_open (course, year, semester, book, teacher, received) values (#{course.id}, #{year}, #{semester}, #{book.isbn}, #{teacher.id}, 0)")
    fun insert(courseOpen: CourseOpen): Int

    @Delete("delete from course_open where (course, year, semester) = (#{course.id}, #{year}, #{semester})")
    fun delete(courseOpen: CourseOpen): Int

    @Delete("delete from course_open where id = #{id}")
    fun deleteById(id: Long): Int

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
        @Param("semester") semester: Int,
    ): List<CourseOpen>

    @Update("update course_open set received = 1 where id = #{id}")
    fun requestBook(id: Int): Int

    @Update("update course_open set received = 0 where id = #{id}")
    fun giveUpBook(id: Int): Int

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
}