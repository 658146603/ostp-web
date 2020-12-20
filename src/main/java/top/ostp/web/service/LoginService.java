package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Student;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

@NoAuthority
@Service
public class LoginService {
    StudentMapper studentMapper;
    TeacherMapper teacherMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
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
        return Responses.fail("用户名或密码错误");
    }
}
