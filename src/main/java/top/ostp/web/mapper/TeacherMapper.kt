package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.apache.ibatis.mapping.FetchType
import org.springframework.stereotype.Repository
import top.ostp.web.model.Teacher

@Mapper
@Repository
interface TeacherMapper {

    @Insert("insert into teacher (id, name, college, password, email) values (#{id}, #{name}, #{college.id}, #{password}, #{email})")
    fun insert(teacher: Teacher): Int

    @Insert("insert into teacher (id, name, college, password, email) values (#{id}, #{name}, #{college}, #{password}, #{email})")
    fun insertByVal(
        @Param("id") id: String,
        @Param("name") name: String,
        @Param("college") college: Long,
        @Param("password") password: String,
        @Param("email") email: String,
    ): Int

    @Delete("delete from teacher where id = #{id}")
    fun delete(teacher: Teacher): Int

    @Update("update teacher set name = #{name}, college = #{college.id}, password = #{password}, email = #{email} where id = #{id}")
    fun update(teacher: Teacher): Int

    @Select("select * from teacher where id = #{id} and password = #{password} limit 1")
    @Results(
        value = [
            Result(
                column = "college",
                property = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun login(@Param("id") id: String, @Param("password") password: String): Teacher?

    @Select("select id, name, college, password, email from teacher")
    @Results(
        value = [
            Result(
                column = "college",
                property = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectAll(): List<Teacher>


    @Select("select id, name, college, password, email from teacher where name = #{name}")
    @Results(
        value = [
            Result(
                column = "college",
                property = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectByName(name: String): List<Teacher>

    @Select("select id, name, college, password, email from teacher where id = #{id} limit 1")
    @Results(
        value = [
            Result(
                column = "college",
                property = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectById(id: String): Teacher?

    @Results(
        value = [
            Result(
                column = "college",
                property = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    @ResultType(Teacher::class)
    @Select("select * from teacher where name like concat('%', #{name}, '%')")
    fun likeListByName(@Param("name") name: String): List<Teacher>
}