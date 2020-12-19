package top.ostp.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondHandPublishMapper {
    @Insert("insert into second")
}
