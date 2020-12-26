package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import top.ostp.web.model.Course
import top.ostp.web.model.annotations.AuthAdmin
import top.ostp.web.model.annotations.AuthStudent
import top.ostp.web.model.annotations.AuthTeacher
import top.ostp.web.model.annotations.NoAuthority
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.service.CourseService

@Controller
class CourseController {

    @Autowired
    lateinit var courseService: CourseService

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = ["/course/list", "/course/all"])
    @ResponseBody
    fun list(): ApiResponse<List<Course>> = courseService.list()

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = ["/course/list/by/major"])
    @ResponseBody
    fun listByMajor(major: Long): ApiResponse<List<Course>> = courseService.listByMajor(major)

    @AuthAdmin
    @PostMapping(path = ["/course/add"])
    @ResponseBody
    fun addCourse(id: String, major: Long, name: String): ApiResponse<Any> {
        return courseService.addCourse(id, major, name)
    }

    @AuthAdmin
    @PostMapping(path = ["/course/delete"])
    @ResponseBody
    fun deleteCourse(id: String): ApiResponse<Any> {
        return courseService.deleteCourse(id)
    }

    @NoAuthority
    @PostMapping(path = ["/course/fuzzy"])
    @ResponseBody
    fun fuzzyQuery(name: String): ApiResponse<List<Course>> {
        return courseService.fuzzyQuery(name)
    }

    @NoAuthority
    @PostMapping(path = ["/course/fuzzy/by/major"])
    @ResponseBody
    fun fuzzyWithMajorByName(major: Int, name: String): ApiResponse<List<Course>> {
        return courseService.fuzzyWithMajorByName(major, name)
    }
}