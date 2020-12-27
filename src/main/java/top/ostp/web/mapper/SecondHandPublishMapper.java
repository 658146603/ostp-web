package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.SecondHandPublish;

import java.util.List;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second_hand_publish(id, person, secondId, book, price, exchange, status) values (#{id},#{person.id},#{second?.id},#{book.isbn},#{price},#{exchange},0)")
    int insert(SecondHandPublish secondHandPublish);

    @Update("update second_hand_publish set person=#{person?.id},secondId=#{second.id},book = #{book.isbn},price = #{price},exchange = #{exchange},status = #{status} where id = #{id}")
    int update(SecondHandPublish secondHandPublish);

    @Delete("delete from second_hand_publish where id = #{id}")
    int delete(String id);

    @Select("select * from second_hand_publish where id = #{id} limit 1")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    SecondHandPublish select(String id);

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
    @Select("select * from second_hand_publish where id = #{id}")
    SecondHandPublish selectPart(String id);

    @Select("select * from second_hand_publish")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
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
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandPublish> selectPublishByStudentId(String id);

    @Select("select * from second_hand_publish where book = #{isbn}")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandPublish> selectPublishByISBN(String isbn);


    /**
     * 查找某个人的可购买列表，是没有交易的，且状态是购买的
     *
     * @param name      书名
     * @param studentId 学生的id
     * @return 求购列表
     */
    @Select("select second_hand_publish.* from second_hand_publish\n" +
            "join" +
            " (select * from book where book.name like concat('%', #{name}, '%')) book on book.isbn = second_hand_publish.book\n" +
            "join (select * from student where student.name like concat('%', #{publisher}, '%')) student on second_hand_publish.person = student.id\n" +
            "where second_hand_publish.exchange = 0\n" +
            "  and second_hand_publish.status = 0\n" +
            "  and second_hand_publish.person != #{studentId}\n" +
            "order by book.name, student.id\n")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandPublish> selectBuyListByNamePublisherExceptStudent(@Param("name") String name, @Param("studentId") String studentId, @Param("publisher") String publisher);


    /**
     * 查找某个人的可交换列表，是没有交易的，且状态是购买的
     *
     * @param name      书名
     * @param studentId 学生的id
     * @return 可交换列表
     */
    @Select("select second_hand_publish.* from second_hand_publish\n" +
            "join" +
            " (select * from book where book.name like concat('%', #{name}, '%')) book on book.isbn = second_hand_publish.book\n" +
            "join (select * from student where student.name like concat('%', #{publisher}, '%')) student on second_hand_publish.person = student.id\n" +
            "where second_hand_publish.exchange = 1\n" +
            "  and second_hand_publish.status = 0\n" +
            "  and second_hand_publish.person != #{studentId}\n" +
            "order by book.name, student.id\n")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandFindMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandPublish> selectExchangeListByNamePublisherExceptStudent(@Param("name") String name, @Param("studentId") String studentId, @Param("publisher") String publisher);
}
