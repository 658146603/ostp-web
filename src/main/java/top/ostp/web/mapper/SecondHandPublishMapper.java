package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;

import java.util.List;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second_hand_publish(person, book, price, exchange, status) values (#{person.id},#{book.id},#{price},#{exchange},#{status})")
    int insert(SecondHandPublish secondHandPublish);

    @Update("update second_hand_publish set book = #{book.id},price = #{price},exchange = #{exchange},status = #{status} where person = #{person.id}")
    int update(SecondHandPublish secondHandPublish);

    @Delete("delete from second_hand_publish where book = #{book.id}")
    int deleteByBookId(Book book);
//
    @Select("select * from second_hand_publish")
    List<SecondHandPublish> selectAll();

    @Select("select * from second_hand_publish where person = #{person.id}")
    List<SecondHandPublish> selectPublishByStudentId();

}
