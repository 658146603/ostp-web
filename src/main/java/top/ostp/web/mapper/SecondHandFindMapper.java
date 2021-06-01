package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import top.ostp.web.model.SecondHandFind;

import java.util.List;

@Mapper
@Repository
public interface SecondHandFindMapper {

    @Insert(value = "insert into second_hand_find(id, person, secondId, book, price, exchange, status)\n" +
            "VALUES (#{id}, #{person.id}, #{second.id}, #{book.isbn}, #{price}, #{exchange}, #{status})")
    int insert(SecondHandFind secondHandFind);

    @Insert(value = "insert into second_hand_find(id, person, secondId, book, price, exchange, status)\n" +
            "VALUES (#{id}, #{person.id}, #{second.id}, #{book.isbn}, #{price}, #{exchange}, #{status})")
    int insertAll(SecondHandFind secondHandFind);

    @Delete("delete\n" +
            "from second_hand_find\n" +
            "where id = #{id}")
    int delete(SecondHandFind secondHandFind);

    @Update("update second_hand_find\n" +
            "set person=#{person.id},\n" +
            "    secondId=#{second.id},\n" +
            "    book=#{book.isbn},\n" +
            "    price=#{price},\n" +
            "    exchange=#{exchange},\n" +
            "    status=#{status}\n" +
            "where id = #{id}")
    int update(SecondHandFind secondHandFind);

    @Select("select * from second_hand_find where id=#{id}")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    SecondHandFind select(String id);

    /**
     * 只选择部分依赖，用于解决循环construct的问题
     * @param id 订单的id
     * @return 订单的部分数据，排除second
     */
    @Select("select * from second_hand_find where id=#{id}")
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
    SecondHandFind selectPart(String id);

    @Select("select * from second_hand_find where book = #{isbn}")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    @ResultType(SecondHandFind.class)
    List<SecondHandFind> selectByBook(String isbn);

    @Select("select id, person, book, price, exchange, status\n" +
            "from second_hand_find")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandFind> selectAll();

    @Select("select * from second_hand_find where person =#{id}")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandFind> selectByStudentId(String id);

    @Select("select * from second_hand_find where person =#{studentId} and book = #{isbn} and exchange = 0 and status = 0")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    List<SecondHandFind> selectBuyListByStudentAndBook(@Param("studentId") String studentId, @Param("isbn") String isbn);

    @Select("select * from second_hand_find where person = #{studentId} and book = #{isbn} and secondId is null and exchange = 1 and status = 0 limit 1")
    @Results(
            value = {
                    @Result(
                            property = "person", column = "person",
                            one = @One(select = "top.ostp.web.mapper.StudentMapper.selectStudentById", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "second", column = "secondId",
                            one = @One(select = "top.ostp.web.mapper.SecondHandPublishMapper.selectPart", fetchType = FetchType.EAGER)
                    ),
                    @Result(
                            property = "book", column = "book",
                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
                    )
            }
    )
    SecondHandFind selectFindByStudentAndBookAndAvailable(@Param("studentId") String studentId, @Param("isbn") String isbn);

    /**
     * 某个人的寻求尚未进行交换的寻求交换列表，返回的是自己的发布交换列表
     *
     * @param otherId 对方学生的id
     * @param selfId  本人的id
     * @return 发布交换列表
     */
    @Select("select second_hand_find.*\n" +
            "from second_hand_find\n" +
            "where second_hand_find.exchange = 1\n" +
            "  and second_hand_find.status = 0\n" +
            "  and second_hand_find.person = #{otherId}\n"
    )
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
    List<SecondHandFind> selectByStudentIdNotExchanged(@Param("otherId") String otherId, @Param("selfId") String selfId);
}
