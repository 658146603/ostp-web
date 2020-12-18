package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.CollegeMapper
import top.ostp.web.model.College

@SpringBootTest
class CollegeTests {
    @Autowired
    lateinit var mapper: CollegeMapper

    @Test
    // DONE
    fun getColleges() {
        val colleges = mapper.selectAll()
        colleges.forEach(::println)
    }
    //DONE
    @Test
    fun getCollegeByName() {
        val collage = mapper.selectByName("计算机科学与技术学院")
        println(collage)
    }

    @Test
    fun insertAndDeleteCollege() {
        mapper.insert(College("理学院"))
        mapper.selectByName("理学院")?.let { mapper.deleteById(it.id) }
    }
}