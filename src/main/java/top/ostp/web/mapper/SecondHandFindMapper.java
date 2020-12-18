package top.ostp.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ostp.web.model.SecondHandFind;

@Mapper
@Repository
public interface SecondHandFindMapper {
    @Insert("insert into second_hand_find(person, book, price, exchange, status) VALUES (#{person.id},#{book.id},#{price},#{exchange},#{status})")
    int insert(SecondHandFind secondHandFind);
}
