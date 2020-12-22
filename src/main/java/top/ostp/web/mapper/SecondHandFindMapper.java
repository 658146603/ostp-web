package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.annotations.Blame;

import java.util.List;

@Mapper
@Repository
public interface SecondHandFindMapper {
    @Insert("insert into second_hand_find(id, person, book, price, exchange, status)\n" +
            "VALUES (#{id}, #{person.id}, #{book.isbn}, #{price}, #{exchange}, #{status})")
    int insert(SecondHandFind secondHandFind);

    @Delete("delete\n" +
            "from second_hand_find\n" +
            "where id = #{id}")
    int delete(SecondHandFind secondHandFind);

    @Update("update second_hand_find\n" +
            "set person=#{person.id},\n" +
            "    book=#{book.idbn},\n" +
            "    price=#{price},\n" +
            "    exchange=#{exchange},\n" +
            "    status=#{status}\n" +
            "where id = #{id}")
    int update(SecondHandFind secondHandFind);

    @Select("select *\n" +
            "from second_hand_find\n" +
            "where id = #{id}")
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
    SecondHandFind select(String id);

    @Select("select *\n" +
            "from second_hand_find\n" +
            "where book = #{id}")
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
    SecondHandFind selectByBook(String id);


    @Select("select id, person, book, price, exchange, status\n" +
            "from second_hand_find")
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
    List<SecondHandFind> selectAll();

    @Select("select * from second_hand_find where person =#{id}")
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
//    @Select("select book.isbn from book,second_hand_find where book.isbn = second_hand_find.book and person = #{id} ")
//        @Results(
//            value = {
//                    @Result(
//                            property = "book", column = "book",
//                            one = @One(select = "top.ostp.web.mapper.BookMapper.selectByISBN", fetchType = FetchType.EAGER)
//                    )
//
//            }
//    )
    List<SecondHandFind> selectBookByStudentId(String id);
}
