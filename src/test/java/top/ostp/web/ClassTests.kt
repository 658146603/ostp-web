package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.ClassMapper
import top.ostp.web.mapper.MajorMapper
import top.ostp.web.model.Class

@SpringBootTest
//TODO
class ClassTests {
    @Autowired
    lateinit var majorMapper: MajorMapper

    @Autowired
    lateinit var classMapper: ClassMapper

    @Test
    fun insert() {
        val major = majorMapper.selectByName("计算机科学与技术")
        val clazz = Class("计算机科学与技术1802", major!!)
        classMapper.insert(clazz)
    }

    @Test
    fun update() {
        val clazz = classMapper.selectByName("计算机科学与技术1802").firstOrNull()!!
        clazz.name = "计算机科学与技术1801"
        classMapper.update(clazz)
    }

    @Test
    fun select() {
        val classes = classMapper.selectAll()
        classes.forEach(::println)
        println()
        val clazz = classMapper.selectByName("计算机科学与技术1801")
        println(clazz)
    }

    @Test
    fun delete() {
        val clazz = classMapper.selectByName("计算机科学与技术1801").firstOrNull()!!
        classMapper.delete(clazz)
    }

}