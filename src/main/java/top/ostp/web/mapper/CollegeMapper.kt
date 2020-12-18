package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import top.ostp.web.model.College
import org.springframework.stereotype.Repository

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
    fun selectById(id: Int): College?

    @Select("select * from college")
    @ResultType(College::class)
    fun selectAll(): List<College>
}