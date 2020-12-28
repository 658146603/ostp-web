package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import top.ostp.web.model.Clazz
import top.ostp.web.model.complex.ClassAdvice

@Mapper
@Repository
interface ClazzMapper {
    @Insert("insert into clazz (name, major) value (#{name}, #{major.id})")
    fun insert(clazz: Clazz): Int

    @Update("update clazz set name = #{name} where id = #{id}")
    fun update(clazz: Clazz): Int

    @Delete("delete from clazz where id = #{id}")
    fun delete(clazz: Clazz): Int

    @Select("select * from clazz")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Clazz::class)
    fun selectAll(): List<Clazz>

    @Select("select * from clazz where id = #{id} limit 1")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    fun selectById(id: Long): Clazz?

    @Select("select * from clazz where name = #{name}")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Clazz::class)
    fun selectByName(name: String): List<Clazz>

    @Select("select * from clazz where major = #{id}")
    @Results(
        value = [
            Result(
                property = "major", column = "major",
                one = One(select = "top.ostp.web.mapper.MajorMapper.selectById")
            )
        ]
    )
    @ResultType(Clazz::class)
    fun selectAllByMajorId(id: Int): List<Clazz>

    @Select(
        """
select clazz.*,
    (select count(*) from student where student.clazz = clazz.id) studentCount
    from clazz where clazz.major = #{id}
    """
    )
    @ResultType(ClassAdvice::class)
    fun selectAllExtendByMajorId(id: Int): List<ClassAdvice>

    @Select(
        """
select *
from clazz
where major in (select id
                from major
                where college = #{id})
       
    """
    )
    @ResultType(Clazz::class)
    fun selectAllByCollegeId(id: Int): List<Clazz>
}