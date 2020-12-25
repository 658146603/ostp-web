package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Book
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.complex.BookAdvice

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



    @Select("select distinct book.* from book left join course_open co on book.isbn = co.book left join course cou on co.course = cou.id where book.name like concat('%', #{name}, '%') and cou.name like concat('%', #{course}, '%')")
    @ResultType(Book::class)
    fun selectByQueryParameters(name: String, course: String): List<Book>

    @Select("select * from book where isbn = #{isbn,jdbcType=VARCHAR} limit 1")
    fun selectByISBN(isbn: String): Book?


    @Select("select distinct cou.name from book left join course_open co on book.isbn = co.book left join course cou on co.course = cou.id where book.isbn = #{isbn}")
    fun selectRelatedCoursesByISBN(isbn: String): List<String>

    @Select("select course_open.* from book left join course_open on book.isbn = course_open.book where book.isbn = #{isbn}")
    fun selectRelatedCourseOpensByISBN(isbn: String): List<CourseOpen>

    @Update("update book set name = #{name,jdbcType=VARCHAR}, price = #{price,jdbcType=DECIMAL} where isbn = #{isbn,jdbcType=VARCHAR}")
    fun updateByISBN(record: Book): Int

    @Select("select * from book where name like concat('%',#{name},'%') limit 10")
    fun fuzzyQuery(name:String): List<Book>
}