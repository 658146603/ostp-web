package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.apache.ibatis.mapping.FetchType
import org.springframework.stereotype.Repository
import top.ostp.web.model.Major

@Mapper
@Repository
interface MajorMapper {

    @Select("select * from major")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    @ResultType(Major::class)
    fun selectAll(): List<Major>

    @Select("select * from major where id = #{id}")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectById(id: Long): Major?

    @Select("select * from major where name = #{name}")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectByName(name: String): Major?

    @Insert("insert into major (name, college) values (#{name}, #{college.id})")
    fun insert(major: Major): Int

    @Update("update major set name = #{name} where id = #{id}")
    fun update(major: Major): Int

    @Update("delete from major where id = #{id}")
    fun delete(major: Major): Int
}