package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Class

@Mapper
@Repository
interface ClassMapper {
    @Insert("insert into class (name, major) value (#{name}, #{major.id})")
    fun insert(clazz: Class): Int

    @Update("update class set name = #{name} where id = #{id}")
    fun update(clazz: Class): Int

    @Delete("delete from class where id = #{id}")
    fun delete(clazz: Class): Int

    @Select("select * from class")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Class::class)
    fun selectAll(): List<Class>

    @Select("select * from class where id = #{id} limit 1")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    fun selectById(id: Long): Class?

    @Select("select * from class where name = #{name}")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Class::class)
    fun selectByName(name: String): List<Class>
}