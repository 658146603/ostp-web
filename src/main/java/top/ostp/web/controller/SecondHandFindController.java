package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.SecondHandFindService;

@Controller
public class SecondHandFindController {
    SecondHandFindService secondHandFindService;

    @Autowired
    public void setSecondHandFindService(SecondHandFindService secondHandFindService) {
        this.secondHandFindService = secondHandFindService;
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/select/all")
    @ResponseBody
    public ApiResponse<Object> selectAll() {
        return secondHandFindService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/select/{id}")
    @ResponseBody
    public ApiResponse<Object> select(@PathVariable String id) {
        return secondHandFindService.select(id);

    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/selectPerson")
    @ResponseBody
    public ApiResponse<Object> selectByPerson(String person) {
        return secondHandFindService.selectByStudentId(person);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/selectByBook")
    @ResponseBody
    public ApiResponse<Object> selectByBook(String isbn) {
        return secondHandFindService.selectByBook(isbn);
    }


}
