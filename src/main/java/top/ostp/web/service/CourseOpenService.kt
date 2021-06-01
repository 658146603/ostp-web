package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.BookMapper
import top.ostp.web.mapper.CourseMapper
import top.ostp.web.mapper.CourseOpenMapper
import top.ostp.web.mapper.TeacherMapper
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses

@Service
class CourseOpenService {
    @Autowired
    lateinit var courseOpenMapper: CourseOpenMapper

    @Autowired
    lateinit var courseMapper: CourseMapper

    @Autowired
    lateinit var teacherMapper: TeacherMapper

    @Autowired
    lateinit var bookMapper: BookMapper


    fun insert(course: String, book: String, teacher: String, year: Int, semester: Int): ApiResponse<String> {
        val c = courseMapper.selectById(course)
        val b = bookMapper.selectByISBN(book)
        val t = teacherMapper.selectById(teacher)
        if (c == null || b == null || t == null || year < 0 || semester !in arrayOf(1,2,3)) {
            return Responses.fail("参数错误")
        }
        if (courseOpenMapper.selectByCourse(c).isNotEmpty()) {
            return Responses.fail("重复开课")
        }
        val courseOpen = CourseOpen(c, year, semester, b, t)

        return when (courseOpenMapper.insert(courseOpen)) {
            1 -> Responses.ok("开课成功")
            else -> Responses.fail("开课失败, 请检查参数是否正确")
        }
    }

    fun listAll() = Responses.ok(courseOpenMapper.selectAll())
}