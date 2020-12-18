package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.TeacherService;

import java.util.List;

@Controller(value = "/teacher")
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping(value = "/teacher/insert")
    @ResponseBody
    public ApiResponse<Object> insertTeacher(Teacher teacher){
        return teacherService.insert(teacher);
    }

    @PostMapping(value = "/teacher/selectById")
    @ResponseBody
    public ApiResponse<Teacher> selectById(String id){
        return teacherService.selectById(id);
    }

    @PostMapping(value = "/teacher/selectByName")
    @ResponseBody
    public ApiResponse<List<Teacher>> selectByName(String name){
        return teacherService.selectByName(name);
    }

    @PostMapping(value = "/teacher/selectAll")
    @ResponseBody
    public ApiResponse<List<Teacher>> selectAll(){
        return teacherService.selectAll();
    }

    @PostMapping(value = "/teacher/deleteById")
    @ResponseBody
    public ApiResponse<Object> deleteById(Teacher teacher){
        return teacherService.deleteById(teacher);
    }
}
