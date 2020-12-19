package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.CourseMapper
import top.ostp.web.mapper.MajorMapper
import top.ostp.web.model.Course
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses

@Service
class CourseService {
    @Autowired
    lateinit var courseMapper: CourseMapper

    @Autowired
    lateinit var majorMapper: MajorMapper

    fun list() = Responses.ok(courseMapper.selectAll())


    fun listByMajor(m: Long): ApiResponse<List<Course>> {
        val major = majorMapper.selectById(m) ?: return Responses.fail("Major [$m] Not Found")
        return Responses.ok(courseMapper.selectByMajor(major))
    }

    fun selectById(c: String): ApiResponse<Course> {
        val course = courseMapper.selectById(c) ?: return Responses.fail("Course [$c] Not Found")
        return Responses.ok(course)
    }
}