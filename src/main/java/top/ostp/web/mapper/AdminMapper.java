package top.ostp.web.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import top.ostp.web.model.Admin;

@Mapper
@Repository
public interface AdminMapper {

    @Select("select * from admin where id=#{id} and password=#{password}")
    @Results(
            value = {
                    @Result(
                            property = "college", column = "college",
                            one = @One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
                    )
            }
    )
    Admin login(@Param("id") String id, @Param("password") String password);

    @Insert("insert into admin(id, password, su, college) VALUES (#{id},#{password},0,#{college})")
    int insert(@Param("id") String id, @Param("password") String password, @Param("college") String college);

    @Delete("delete from admin where id=#{admin.id}")
    int delete(@Param("admin") Admin admin);

    @Update("update admin set password=#{password},su=#{su},college=#{college.id} where id=#{id}")
    int update(Admin admin);

    @Select("select * from admin where id=#{id};")
    @Results(
            value = {
                    @Result(
                            property = "college", column = "college",
                            one = @One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
                    )
            }
    )
    Admin select(String id);

    @Update("update admin set password=#{password} where id=#{admin.id} and password=#{admin.password}")
    int updatePassword(@Param("admin") Admin admin, @Param("password") String password);
}
