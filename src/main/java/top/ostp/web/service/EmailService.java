package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Student;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EmailProvider;
import top.ostp.web.util.EncryptProvider;

import java.util.UUID;

@Service
public class EmailService {
    StudentMapper studentMapper;
    TeacherMapper teacherMapper;

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    private boolean email(Object people, String email) {
        String newPassword = String.valueOf(UUID.randomUUID());
        int result = 0;
        if (people instanceof Student) {
            result = studentMapper.updatePassword((Student) people, EncryptProvider.getSaltedPassword(((Student) people).getId(), newPassword));
        } else {
            result = teacherMapper.updatePassword((Teacher) people, EncryptProvider.getSaltedPassword(((Teacher) people).getId(), newPassword));
        }
        if (result == 0) {
            return false;
        } else {
            return EmailProvider.email("密码修改成功", "你的新密码是：\n" + newPassword, email);
        }
    }

    public ApiResponse<Object> resetPassword(String id, String email) {
        Student student = studentMapper.checkEmail(id, email);
        if (student != null) {
            if (email(student, email)) {
                return Responses.ok("已重置密码");
            } else {
                return Responses.fail("邮件服务异常");
            }
        }
        Teacher teacher = teacherMapper.checkEmail(id, email);
        if (teacher != null) {
            if (email(teacher, email)) {
                return Responses.ok("已重置密码");
            } else {
                return Responses.fail("邮件服务异常");
            }
        }
        return Responses.fail("ID或邮箱输入错误");
    }
}
