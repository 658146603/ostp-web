package top.ostp.web.mapper

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import top.ostp.web.model.Book

@Mapper
interface BookMapper {
    @Select("select * from book")
    fun selectAll(): List<Book>

    @Delete("delete from book where isbn = #{isbn,jdbcType=VARCHAR}")
    fun deleteByISBN(isbn: String?): Int

    @Insert("insert into book (isbn, name, price, cover) values (#{isbn}, #{name,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, #{cover,jdbcType=BLOB})")
    fun insert(record: Book?): Int

    @Select("")
    fun selectByISBN(isbn: String?): Book?

    fun updateByISBN(record: Book?): Int
}