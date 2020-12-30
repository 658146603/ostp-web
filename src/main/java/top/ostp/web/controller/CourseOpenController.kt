package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.annotations.AuthAdmin
import top.ostp.web.model.annotations.AuthStudent
import top.ostp.web.model.annotations.AuthTeacher
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.service.CourseOpenService

@Controller
class CourseOpenController {

    @Autowired
    lateinit var service: CourseOpenService

    /**
     * 管理员开设一门课程
     * @param course 课程所需要的课程编号
     * @param book 课程所需要的教材ISBN
     * @param teacher 教授这门课程的老师的编号
     * @param year 开设学年
     * @param semester 开设学期
     * @return ApiResponse 结果
     */
    @AuthAdmin
    @PostMapping(path = ["/course_open/insert"])
    @ResponseBody
    fun insert(
        @RequestParam("course") course: String,
        @RequestParam("book") book: String,
        @RequestParam("teacher") teacher: String,
        @RequestParam("year") year: Int,
        @RequestParam("semester") semester: Int
    ): ApiResponse<String> = service.insert(course, book, teacher, year, semester)

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = ["/course_open/list"])
    @ResponseBody
    fun list(): ApiResponse<List<CourseOpen>> = service.listAll()


}