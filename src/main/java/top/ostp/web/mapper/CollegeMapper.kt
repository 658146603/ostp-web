package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.College

@Mapper
@Repository
interface CollegeMapper {
    @Delete("delete from college where id = #{id}")
    fun deleteById(id: Int): Int

    @Insert("insert into college (name) values (#{name})")
    fun insert(record: College): Int

    @Insert("insert into college (name) values (#{name})")
    fun insertByName(name: String): Int

    @Select("select * from college where name = #{name} limit 1")
    fun selectByName(name: String): College?

    @Select("select * from college where id = #{id} limit 1")
    fun selectById(id: Long): College?

    @Select("select * from college")
    @ResultType(College::class)
    fun selectAll(): List<College>

    @Select("select * from college where name like concat('%', #{name}, '%')")
    @ResultType(College::class)
    fun likeByName(@Param("name") name: String): List<College>
}