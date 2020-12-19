package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;

import java.util.List;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second_hand_publish(id, person, book, price, exchange, status) values (#{id},#{person.id},#{book.isbn},#{price},#{exchange},#{status})")
    int insert(SecondHandPublish secondHandPublish);

    @Update("update second_hand_publish set person=#{person.id},book = #{book.isbn},price = #{price},exchange = #{exchange},status = #{status} where id = #{id}")
    int update(SecondHandPublish secondHandPublish);

    @Delete("delete from second_hand_publish where id = #{id}")
    int deleteByOrderId(String id);

    //
    @Select("select * from second_hand_publish")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )

            }
    )
    List<SecondHandPublish> selectAll();

    @Select("select * from second_hand_publish where person = #{id}")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )

            }
    )
    List<SecondHandPublish> selectPublishByStudentId(String id);

    @Select("select * from second_hand_publish where id = #{id} limit 1")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )

            }
    )
    SecondHandPublish selectPublishByOrderId(String id);

}
