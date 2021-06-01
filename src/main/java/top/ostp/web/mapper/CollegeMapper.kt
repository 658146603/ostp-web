package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.College
import top.ostp.web.model.complex.CollegeAdvance

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

    @Select("select college.* from college, major where college.id = major.college and major.id = #{id}")
    fun selectByMajorId(id: Long): College?

    @Select("select * from college")
    @ResultType(College::class)
    fun selectAll(): List<College>

    @Select("select * from college where name like concat('%', #{name}, '%')")
    @ResultType(College::class)
    fun likeByName(@Param("name") name: String): List<College>

    @Select(
        """select college.*,
       (select count(*) from major where major.college = college.id)     majorCount,
       (select count(*) from teacher where teacher.college = college.id) teacherCount,
       (select count(*)
        from major
                 join clazz on clazz.major = major.id
                 join student on clazz.id = student.clazz
        where major.college = college.id)                                studentCount
from college;
    """
    )
    @ResultType(CollegeAdvance::class)
    fun selectAllExtend(): List<CollegeAdvance>
}