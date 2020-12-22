package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Admin;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

@Service
public class StudentService {
    StudentMapper studentMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public ApiResponse<Object> addStudent(Student student) {
        student.setPassword(EncryptProvider.getSaltedPassword(student.getId(), student.getPassword()));
        try {
            studentMapper.insert(student);
            return Responses.ok("插入成功");
        } catch (DuplicateKeyException e) {
            return Responses.fail("插入失败");
        }
    }

    public ApiResponse<Object> deleteStudent(Student student) {
        int result = studentMapper.delete(student);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> modifyStudent(Student student) {
        int result = studentMapper.update(student);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> findStudent(String id) {
        Student student = studentMapper.selectStudentById(id);
        if (student != null) {
            return Responses.ok(student);
        } else {
            return Responses.fail("未找到该学生");
        }
    }

    public ApiResponse<Object> charge(Student student, int money) {
        int result = studentMapper.changeMoney(student, money);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> charge(String id, int money) {
        Student student = studentMapper.selectStudentById(id);
        if (student == null) {
            return Responses.fail("学生ID不存在");
        }
        int result = studentMapper.changeMoney(student, money);
        if (result == 1) {
            return Responses.ok("充值成功");
        } else {
            return Responses.fail("充值失败");
        }
    }

    public ApiResponse<Object> useMoney(Student student, int money) {
        int result = studentMapper.changeMoney(student, -money);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> useMoney(String id, int money) {
        Student student = studentMapper.selectStudentById(id);
        if (student == null) {
            return Responses.fail("学生ID不存在");
        }
        int result = studentMapper.changeMoney(student, money);
        if (result == 1) {
            return Responses.ok("消费成功");
        } else {
            return Responses.fail("消费失败");
        }
    }

    public ApiResponse<Object> updatePassword(Student student, String password) {
        student.setPassword(EncryptProvider.getSaltedPassword(student.getId(), student.getPassword()));
        password = EncryptProvider.getSaltedPassword(student.getId(), password);
        int status = studentMapper.updatePassword(student, password);
        if (status == 1) {
            return Responses.ok("密码修改成功");
        } else {
            return Responses.fail("错误");
        }
    }
}
