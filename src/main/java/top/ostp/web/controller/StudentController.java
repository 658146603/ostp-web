package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.StudentService;

@Controller
public class StudentController {
    StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/student/insert")
    @ResponseBody
    public ApiResponse<Object> insert(Student student) {
        return studentService.addStudent(student);
    }

    @PostMapping(path = "/student/delete")
    @ResponseBody
    public ApiResponse<Object> delete(Student student) {
        return studentService.deleteStudent(student);
    }

    @PostMapping(path = "/student/select")
    @ResponseBody
    public ApiResponse<Object> select(String id) {
        return studentService.findStudent(id);
    }

    @PostMapping(path = "/student/update")
    @ResponseBody
    public ApiResponse<Object> update(Student student) {
        return studentService.modifyStudent(student);
    }

    @PostMapping(path = "/student/{id}/charge")
    @ResponseBody
    public ApiResponse<Object> charge(int money, @PathVariable String id) {
        return studentService.charge(id, money);
    }

    @PostMapping(path = "/student/{id}/consume")
    @ResponseBody
    public ApiResponse<Object> consume(int money, @PathVariable String id) {
        return studentService.useMoney(id, money);
    }
}
