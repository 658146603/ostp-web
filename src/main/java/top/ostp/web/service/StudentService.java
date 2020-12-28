package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Clazz;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EmailProvider;
import top.ostp.web.util.EncryptProvider;

import java.util.List;

@Service
public class StudentService {
    StudentMapper studentMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public ApiResponse<Object> addStudent(String id, String name, String password, Long clazz_id, String email) {
        Student student = new Student();
        student.setId(id);
        student.setPassword(EncryptProvider.getSaltedPassword(id, password));
        student.setName(name);
        student.setEmail(email);
        Clazz clazz = new Clazz();
        clazz.setId(clazz_id);
        student.setClazz(clazz);
        try {
            studentMapper.insert(student);
            new Thread(() -> {
                EmailProvider.email("账号创建成功", name + " 同学你好！<br>你的账号是 " + id + "<br>你的密码是 " + password, email);
            }).start();
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

    @Deprecated
    public ApiResponse<Object> charge(Student student, int money) {
        int result = studentMapper.changeMoney(student, money);
        if (result == 1) {
            return Responses.ok("充值成功");
        } else {
            return Responses.fail("充值失败");
        }
    }

    public ApiResponse<Object> charge(String id, int money) {
        Student student = studentMapper.selectStudentById(id);
        if (student == null) {
            return Responses.fail("学生ID不存在");
        }

        int balance = student.getBalance();

        int result = studentMapper.changeMoney(student, money);
        Student data = studentMapper.queryMoney(id);
        if (result == 1) {
            if (balance + money != data.getBalance()) {
                //TODO 上线时删除
                System.err.println("消费后余额和预期不一致");
            }
            return Responses.ok("充值成功", data);
        } else {
            return Responses.fail("充值失败", data);
        }
    }

    @Deprecated
    public ApiResponse<Object> useMoney(Student student, int money) {
        int result = studentMapper.changeMoney(student, -money);
        if (result == 1) {
            return Responses.ok("消费成功");
        } else {
            return Responses.fail("消费失败");
        }
    }

    public ApiResponse<Object> useMoney(String id, int money) {
        Student student = studentMapper.selectStudentById(id);
        if (student == null) {
            return Responses.fail("学生ID不存在");
        }

        int balance = student.getBalance();
        if (balance < money) {
            return Responses.fail("余额不足");
        }

        int result = studentMapper.changeMoney(student, -money);
        Student data = studentMapper.queryMoney(id);
        if (result == 1) {
            if (balance - money != data.getBalance()) {
                //TODO 上线时删除
                System.err.println("消费后余额和预期不一致");
            }
            return Responses.ok("消费成功", data);
        } else {
            return Responses.fail("消费失败", data);
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

    public List<Student> selectByClazz(int id) {
        return studentMapper.selectByClazz(id);
    }
}
