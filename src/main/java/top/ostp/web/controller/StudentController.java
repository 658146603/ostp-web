package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Student;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.service.StudentService;

import java.util.List;

@Controller
public class StudentController {
    StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @AuthAdmin
    @PostMapping(path = "/student/insert")
    @ResponseBody
    public ApiResponse<Object> insert(String id, String name, String password, Long clazz, String email) {
        return studentService.addStudent(id, name, password, clazz, email);
    }

    @AuthAdmin
    @PostMapping(path = "/student/delete")
    @ResponseBody
    public ApiResponse<Object> delete(Student student) {
        return studentService.deleteStudent(student);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/student/select")
    @ResponseBody
    public ApiResponse<Object> select(String id) {
        return studentService.findStudent(id);
    }

    @AuthAdmin
    @PostMapping(path = "/student/update")
    @ResponseBody
    public ApiResponse<Object> update(Student student) {
        return studentService.modifyStudent(student);
    }
    /**
     * 对学生进行充值
     * @param money 充值的钱
     * @param id  学生的id
     * @return 充值的状态
     */
    @AuthStudent
    @PostMapping(path = "/student/{id}/charge")
    @ResponseBody
    public ApiResponse<Object> charge(int money, @PathVariable String id) {
        return studentService.charge(id, money);
    }

    @AuthStudent
    @PostMapping(path = "/student/{id}/consume")
    @ResponseBody
    public ApiResponse<Object> consume(int money, @PathVariable String id) {
        return studentService.useMoney(id, money);
    }

    @PostMapping(value = "/student/update/password")
    @ResponseBody
    @AuthStudent
    public ApiResponse<Object> updatePassword(String id, String password0, String password) {
        return studentService.updatePassword(new Student(id, "", null, password0, 0, ""), password);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/student/selectByClass")
    @ResponseBody
    public ApiResponse<List<Student>> selectByClazz(int id) {
        return Responses.ok(studentService.selectByClazz(id));
    }

}
