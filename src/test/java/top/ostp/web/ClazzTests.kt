package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.mapper.MajorMapper
import top.ostp.web.model.Clazz

@SpringBootTest
//TODO
class ClazzTests {
    @Autowired
    lateinit var majorMapper: MajorMapper

    @Autowired
    lateinit var clazzMapper: ClazzMapper

    @Test
    fun insert() {
        val major = majorMapper.selectByName("计算机科学与技术")
        val clazz = Clazz("计算机科学与技术1802", major!!)
        clazzMapper.insert(clazz)
    }

    @Test
    fun update() {
        val clazz = clazzMapper.selectByName("计算机科学与技术1802").firstOrNull()!!
        clazz.name = "计算机科学与技术1801"
        clazzMapper.update(clazz)
    }

    @Test
    fun select() {
        val clazzes = clazzMapper.selectAll()
        clazzes.forEach(::println)
        println()
        val clazz = clazzMapper.selectByName("计算机科学与技术1801")
        println(clazz)
    }

    @Test
    fun delete() {
        val clazz = clazzMapper.selectByName("计算机科学与技术1801").firstOrNull()!!
        clazzMapper.delete(clazz)
    }

}