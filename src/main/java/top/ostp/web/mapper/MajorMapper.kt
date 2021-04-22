package top.ostp.web.mapper

import org.apache.ibatis.annotations.*
import org.apache.ibatis.mapping.FetchType
import org.springframework.stereotype.Repository
import top.ostp.web.model.Major
import top.ostp.web.model.complex.MajorAdvance

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


    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    @ResultType(Major::class)
    @Select("select * from major where college=#{college} and name like concat('%', #{name}, '%')")
    fun fuzzyWithCollege(@Param("college") college: Int, @Param("name") name: String): List<Major>


    @Select("select * from major where major.college = #{collegeId}")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectAllByCollegeId(collegeId: Int): List<Major>

    @Select(
        """
select major.*,
    (select count(*) from clazz where clazz.major = major.id) classCount,
    (select count(*) from clazz join student on clazz.id = student.clazz where clazz.major = major.id) studentCount
    from major where major.college = #{collegeId};
    """
    )
    fun selectAllExtendByCollegeId(collegeId: Int): List<MajorAdvance>

    @Select("select * from major where id = #{id} limit 1")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    fun selectById(id: Long): Major?

    @Select("select * from major where year = #{year}")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    @ResultType(Major::class)
    fun selectByYear(year: Int): List<Major>

    @Select("select * from major where name = #{name}")
    @Results(
        value = [
            Result(
                property = "college", column = "college",
                one = One(select = "top.ostp.web.mapper.CollegeMapper.selectById", fetchType = FetchType.EAGER)
            )
        ]
    )
    @ResultType(Major::class)
    fun selectByName(name: String): List<Major>

    @Insert("insert into major (name, college, year) values (#{name}, #{college}, #{year})")
    fun insert(name: String, college: String, year: String): Int

    @Update("update major set name = #{name} where id = #{id}")
    fun update(major: Major): Int

    @Update("delete from major where id = #{id}")
    fun delete(major: Major): Int
}