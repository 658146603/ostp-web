package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Book

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

    @Select("select * from book where isbn = #{isbn,jdbcType=VARCHAR}")
    fun selectByISBN(isbn: String): Book?

    @Update("update book set name = #{name,jdbcType=VARCHAR}, price = #{price,jdbcType=DECIMAL} where isbn = #{isbn,jdbcType=VARCHAR}")
    fun updateByISBN(record: Book): Int
}