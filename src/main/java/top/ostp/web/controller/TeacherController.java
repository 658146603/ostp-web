package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.service.TeacherService;

import java.util.List;

@Controller(value = "/teacher")
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @AuthAdmin
    @PostMapping(value = "/teacher/insert")
    @ResponseBody
    public ApiResponse<Object> insertTeacher(String id, String name, Long college, String password, String email) {
        return teacherService.insert(id, name, college, password, email);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/teacher/selectById")
    @ResponseBody
    public ApiResponse<Teacher> selectById(String id) {
        return teacherService.selectById(id);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/teacher/selectByName")
    @ResponseBody
    public ApiResponse<List<Teacher>> selectByName(String name) {
        return teacherService.selectByName(name);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = {"/teacher/selectAll", "teacher/list"})
    @ResponseBody
    public ApiResponse<List<Teacher>> selectAll() {
        return teacherService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = {"/teacher/fuzzy"})
    @ResponseBody
    public ApiResponse<List<Teacher>> likeListByName(String name) {
        return teacherService.likeByName(name);
    }

    @AuthAdmin
    @PostMapping(value = "/teacher/deleteById")
    @ResponseBody
    public ApiResponse<Object> deleteById(Teacher teacher) {
        return teacherService.deleteById(teacher);
    }

    @PostMapping(value = "/teacher/update/password")
    @ResponseBody
    @NoAuthority
    public ApiResponse<Object> updatePassword(String id, String password0, String password) {
        return teacherService.updatePassword(new Teacher(id, "", null, password0, ""), password);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/teacher/selectByCollegeId")
    @ResponseBody
    public ApiResponse<List<Teacher>> selectByCollegeId(int collegeId) {
        return Responses.ok(teacherService.selectByCollegeId(collegeId));
    }
}
