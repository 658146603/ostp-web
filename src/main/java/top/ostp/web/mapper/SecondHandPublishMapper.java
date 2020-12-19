package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;

import java.util.List;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second_hand_publish(person, book, price, exchange, status) values (#{person.id},#{book.id},#{price},#{exchange},#{status})")
    int insert(SecondHandPublish secondHandPublish);

    @Update("update second_hand_publish set person = #{person.id},book = #{book.id},price = #{price},exchange = #{exchange},status = #{status} ")
    int update(SecondHandPublish secondHandPublish);

    @Delete("delete from second_hand_publish where book = #{book.id}")
    int deleteByBoodId(Book book);
//
    @Select("select * from second_hand_publish")
    List<SecondHandPublish> selectAll();

    @Select("select * from second_hand_publish where person = #{person.id}")
    List<SecondHandPublish> selectPublishByStudentId();

}
