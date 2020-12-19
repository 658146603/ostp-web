package top.ostp.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.ostp.web.mapper.CollegeMapper
import top.ostp.web.mapper.MajorMapper
import top.ostp.web.model.Major

@SpringBootTest
//TODO
class MajorTests {
    @Autowired lateinit var majorMapper: MajorMapper

    @Autowired lateinit var collegeMapper: CollegeMapper

    @Test
    fun insertMajor() {
        val college = collegeMapper.selectByName("信息工程学院")
        val major = college?.let { Major("电子科学与技术1", it, 2020) }

        if (major != null) {
            majorMapper.insert(major)
        }
    }

    @Test
    fun selectAllMajor() {
        val majors = majorMapper.selectAll()
        majors.forEach(::println)
    }

    @Test
    fun updateMajor() {
        val major = majorMapper.selectByName("电子科学与技术1").firstOrNull()!!
        major.name = "电子科学与技术"
        majorMapper.update(major)
    }

    @Test
    fun deleteMajor() {
        val major = majorMapper.selectByName("电子科学与技术").firstOrNull()!!
        majorMapper.delete(major)
    }

}