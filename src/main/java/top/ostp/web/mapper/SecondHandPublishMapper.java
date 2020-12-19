package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;

import java.util.List;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second_hand_publish(person, book, price, exchange, status) values (#{person.id},#{book.id},#{price},#{exchange},#{status})")
    int insert(SecondHandPublish secondHandPublish);

    @Update("update second_hand_publish set book = #{book.id},price = #{price},exchange = #{exchange},status = #{status} where person = #{id}")
    int update(SecondHandPublish secondHandPublish);

    @Delete("delete from second_hand_publish where book = #{book.isbn}")
    int deleteByBookISBN(Book book);
//
    @Select("select * from second_hand_publish")
    List<SecondHandPublish> selectAll();

    @Select("select * from second_hand_publish where person = #{id}")
    @Results(
            value = {
                    @Result(
                            property = "person",column = "person",
                            one = @One(select ="top.ostp.web.mapper.StudentMapper.selectStudentById",fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book",column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN",fetchType = FetchType.EAGER)
                    )

            }
    )
    List<SecondHandPublish> selectPublishByStudentId(String id);

}
