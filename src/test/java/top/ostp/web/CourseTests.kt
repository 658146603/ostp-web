package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.CourseMapper
import top.ostp.web.mapper.MajorMapper
import top.ostp.web.model.Course

@SpringBootTest
class CourseTests {
    @Autowired lateinit var majorMapper: MajorMapper
    @Autowired lateinit var courseMapper: CourseMapper

    @Test fun insert() {
        val major0 = majorMapper.selectByName("软件工程").firstOrNull()!!
        val major1 = majorMapper.selectByName("计算机科学与技术").firstOrNull()!!

        println(courseMapper.insert(Course("G101030", major0, "数据结构0")))
        println(courseMapper.insert(Course("G101031", major0, "数据结构1")))
        println(courseMapper.insert(Course("G101032", major1, "数据结构0")))

    }

    @Test fun select() {
        val major = majorMapper.selectByName("软件工程").firstOrNull()!!

        val courses = courseMapper.selectAll()
        courses.forEach(::println)
        println()
        val course0 = courseMapper.selectByName("数据结构0")
        course0.forEach(::println)
        println()
        val course1 = courseMapper.selectById("G101010")
        println(course1)
        println()
        val courses0 = courseMapper.selectByMajor(major)
        courses0.forEach(::println)
    }


    @Test fun update() {
        val course0 = courseMapper.selectByName("数据结构0").firstOrNull()!!
        course0.name = "数据结构"
        println(courseMapper.update(course0))
    }

    @Test fun delete() {
        println(courseMapper.deleteById("G101032"))
    }
}