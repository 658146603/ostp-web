package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.AdminMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Admin;
import top.ostp.web.model.Student;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

@Service
public class LoginService {
    StudentMapper studentMapper;
    TeacherMapper teacherMapper;
    AdminMapper adminMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public ApiResponse<Object> login(String id, String password) {
        password = EncryptProvider.getSaltedPassword(id, password);
        Teacher teacher = teacherMapper.login(id, password);
        if (teacher != null) {
            return Responses.ok("教师登录成功", teacher);
        }
        Student student = studentMapper.login(id, password);
        if (student != null) {
            return Responses.ok("学生登录成功", student);
        }
        Admin admin = adminMapper.login(id, password);
        if (admin != null) {
            return Responses.ok("管理员登录", admin);
        }
        return Responses.fail("用户名或密码错误");
    }

    public Object findById(Object role) {
        if (role instanceof Teacher) {
            return teacherMapper.selectById(((Teacher) role).getId());
        }
        if (role instanceof Student) {
            return studentMapper.selectStudentById(((Student) role).getId());
        }
        if (role instanceof Admin) {
            return adminMapper.select(((Admin) role).getId());
        }
        return null;
    }

    public ApiResponse<Boolean> checkDuplicate(String id) {
        if (id == null || id.isEmpty()) {
            return Responses.ok(true);
        }
        Teacher teacher = teacherMapper.selectById(id);
        Student student = studentMapper.selectStudentById(id);
        Admin admin = adminMapper.select(id);
        if (teacher == null && student == null && admin == null) {
            return Responses.ok(false);
        } else {
            return Responses.ok(true);
        }
    }
}
