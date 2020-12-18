package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.CollegeMapper
import top.ostp.web.mapper.TeacherMapper
import top.ostp.web.model.College
import top.ostp.web.model.Teacher

@SpringBootTest
//TODO
class TeacherTests {

    @Autowired
    lateinit var mapper: TeacherMapper

    @Autowired
    lateinit var collegeMapper: CollegeMapper

    @Test
    fun testSelectByName() {
        val teacher = mapper.selectByName("韩珊珊")
        println(teacher)
    }

    @Test
    fun testSelectById() {
        val teacher = mapper.selectById("123456")
        println(teacher)
    }

    @Test
    fun testInsertTeacher() {
        val college = collegeMapper.selectByName("计算机科学与技术学院")
        val teacher = Teacher("123458", "韩珊珊", college, "123457", "hss2@zjut.edu.cn")
        mapper.insert(teacher)
    }

    @Test
    fun testDeleteTeacher() {
        val teacher = mapper.selectById("123458")
        if (teacher != null) {
            mapper.delete(teacher)
        }
    }
}