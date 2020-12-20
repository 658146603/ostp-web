package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import top.ostp.web.model.Course
import top.ostp.web.model.annotations.AuthStudent
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.service.CourseService

@Controller
class CourseController {

    @Autowired
    lateinit var courseService: CourseService

    @PostMapping(path = ["/course/list", "/course/all"])
    @ResponseBody
    fun list(): ApiResponse<List<Course>> = courseService.list()

    @PostMapping(path = ["/course/list/by/major"])
    @ResponseBody
    @AuthStudent
    fun listByMajor(major: Long): ApiResponse<List<Course>> = courseService.listByMajor(major)
}