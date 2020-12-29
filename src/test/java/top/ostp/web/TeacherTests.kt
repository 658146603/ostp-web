package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.CollegeMapper
import top.ostp.web.mapper.TeacherMapper
import top.ostp.web.model.Teacher

@SpringBootTest
class TeacherTests {

    @Autowired
    lateinit var mapper: TeacherMapper

    @Autowired
    lateinit var collegeMapper: CollegeMapper

    @Test
    fun list() {
        mapper.selectAll().forEach(::println)
    }

    @Test
    fun fuzzy() {
        mapper.likeListByName("松").forEach(::println)
    }

    @Test
    fun selectByName() {
        val teacher = mapper.selectByName("韩珊珊")
        println(teacher)
    }

    @Test
    fun selectById() {
        val teacher = mapper.selectById("123456")
        println(teacher)
    }

    @Test
    fun insertTeacher() {
        val college = collegeMapper.selectByName("计算机科学与技术学院")
        val teacher = Teacher("123458", "韩珊珊", college, "123457", "hss2@zjut.edu.cn")
        mapper.insert(teacher)
    }

    @Test
    fun deleteTeacher() {
        val teacher = mapper.selectById("123458")
        if (teacher != null) {
            mapper.delete(teacher)
        }
    }

    @Test
    //TODO
    fun login() {
        var teacher = mapper.login("123456", "123456")
        println(teacher)
        teacher = mapper.login("123458", "123456")
        println(teacher)
    }
}