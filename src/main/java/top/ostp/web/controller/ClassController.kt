package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import top.ostp.web.model.Clazz
import top.ostp.web.model.annotations.AuthAdmin
import top.ostp.web.model.annotations.AuthStudent
import top.ostp.web.model.annotations.AuthTeacher
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses
import top.ostp.web.model.complex.ClassAdvice
import top.ostp.web.service.ClassService

@Controller
@RequestMapping("/class")
class ClassController {
    @Autowired
    lateinit var classService: ClassService

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @ResponseBody
    @PostMapping("/fetch_all")
    fun selectAllByMajorId(id: Int): ApiResponse<List<ClassAdvice>> {
        return Responses.ok(classService.selectAllByMajorId(id))
    }

    /**
     * 按照id值查询学院
     *
     * @param id 专业的编号
     * @return list<Clazz> 该专业下所有的班级
     */
    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @ResponseBody
    @PostMapping("/selectByMajor")
    fun selectByMajorId(id: Int): ApiResponse<List<Clazz>> {
        return Responses.ok(classService.selectByMajor(id))
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/select")
    @ResponseBody
    fun selectById(id: Int): ApiResponse<Clazz?> {
        return Responses.ok(classService.selectById(id))
    }
    /**
     * 管理员添加一门课程
     *
     * @param name 班级的名字
     * @param major 专业的id
     * @return response
     */
    @PostMapping("/insert")
    @ResponseBody
    fun insert(name: String, major: Long): ApiResponse<Any> {
        return classService.insert(name, major)
    }
    /**
     * 根据学院做一个嵌套子查询。查询到该学院下所有的班级
     *
     * @param id 学院的id
     * @return response
     */
    @ResponseBody
    @PostMapping("/fetch_college")
    fun selectAllByCollegeId(id: Int): ApiResponse<List<Clazz>> {
        return classService.selectByCollegeId(id)
    }

}