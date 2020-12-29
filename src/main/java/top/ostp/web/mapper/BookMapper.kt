package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Book
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.complex.SearchParams

@Mapper
@Repository
interface BookMapper {

    @Select("select * from book")
    @ResultType(Book::class)
    fun selectAll(): List<Book>

    @Delete("delete from book where isbn = #{isbn,jdbcType=VARCHAR}")
    fun deleteByISBN(isbn: String): Int

    @Delete("delete from book where isbn = #{isbn,jdbcType=VARCHAR}")
    fun delete(book: Book): Int

    @Insert("insert into book (isbn, name, price, cover) values (#{isbn}, #{name,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, #{cover,jdbcType=VARCHAR})")
    fun insert(record: Book): Int

    @Select("""
    select book.* from (select * from book where book.name like concat('%', #{book}, '%')) book
        left join course_open on book.isbn = course_open.book
        left join course on course_open.course = course.id
        where course.name like concat('%', #{course}, '%')
    """)
    @ResultType(Book::class)
    fun selectByNameAndCourse(@Param("name") name: String, @Param("course") course: String): List<Book>

    @Deprecated("已迁移到searchOfStudent")
    @Select("""
    select distinct book.* from (select * from book where book.name like concat('%', #{name}, '%')) book
        left join course_open on book.isbn = course_open.book
        left join course on course_open.course = course.id
        left join major on course.major = major.id
        where course.name like concat('%', #{course}, '%') and major in (
            select major.id from (select * from student where student.id = #{studentId}) student
                left join clazz on student.clazz = clazz.id
                left join major on clazz.major = major.id
            );
    """)
    @ResultType(Book::class)
    fun selectByStudentNameAndCourse(
        @Param("studentId") studentId: String,
        @Param("name") name: String,
        @Param("course") course: String,
    ): List<Book>

    @Select("""select distinct book.* from (select * from book where book.name like concat('%', #{name}, '%')) book
    join (select * from course_open where course_open.year = #{year} and course_open.semester = #{semester}) course_open on book.isbn = course_open.book
    join course on course_open.course = course.id
    join major on course.major = major.id
    where course.name like concat('%', #{course}, '%') and major in (
        select major.id from (select * from student where student.id = #{personId}) student
            join clazz on student.clazz = clazz.id
            join major on clazz.major = major.id
        );
    """)
    @ResultType(Book::class)
    fun searchOfStudent(searchParams: SearchParams): List<Book>

    @Deprecated("已迁移到searchOfTeacher")
    @Select("""
    select distinct book.* from (select * from book where book.name like concat('%', #{name} ,'%')) book
        left join course_open on book.isbn = course_open.book
        left join course on course_open.course = course.id
    where course.name like concat('%', #{course} ,'%')
        and course_open.teacher = #{teacherId};
    """)
    @ResultType(Book::class)
    fun selectByTeacherNameAndCourse(
        @Param("teacherId") teacherId: String,
        @Param("name") name: String,
        @Param("course") course: String
    ): List<Book>

    @Select("""
    select distinct book.* from (select * from book where book.name like concat('%', #{name} ,'%')) book
        join (select * from course_open where course_open.year = #{year} and course_open.semester = #{semester}) course_open on book.isbn = course_open.book
        join course on course_open.course = course.id
    where course.name like concat('%', #{course} ,'%')
        and course_open.teacher = #{personId};
    """)
    @ResultType(Book::class)
    fun searchOfTeacher(searchParams: SearchParams): List<Book>

    @Select("select * from book where isbn = #{isbn,jdbcType=VARCHAR} limit 1")
    fun selectByISBN(isbn: String): Book?

    @Update("update book set name = #{name,jdbcType=VARCHAR}, price = #{price,jdbcType=DECIMAL} where isbn = #{isbn,jdbcType=VARCHAR}")
    fun updateByISBN(record: Book): Int

    @Select("select * from book where name like concat('%',#{name},'%') limit 10")
    fun fuzzyQuery(name: String): List<Book>
}