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
    fun selectAllByMajorId(id: Int) : ApiResponse<List<Clazz>> {
        return classService.selectAllByMajorId(id)
    }
}