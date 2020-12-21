package top.ostp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.mapper.ClazzMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.mapper.BookMapper;

import java.util.List;

@SpringBootTest
public class StudentTests {
    StudentMapper studentMapper;
    ClazzMapper clazzMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setClazzMapper(ClazzMapper clazzMapper) {
        this.clazzMapper = clazzMapper;
    }

    @Test
    public void insert() {
        Student student = new Student("201806061302", "陈力", clazzMapper.selectByName("计算机科学与技术学院实验班1801").get(0), "123456", 100, "201806061302@zjut.edu.cn");
        studentMapper.insert(student);
    }

    @Test
    public void delete() {
        Student student = new Student();
        student.setId("201806061302");
        studentMapper.delete(student);
    }

    @Test
    public void update() {
        Student student = new Student("201806061302", "陈力", clazzMapper.selectByName("计算机科学与技术学院实验班1801").get(0), "123456", 10000, "201806061302@zjut.edu.cn");
        studentMapper.update(student);
    }

    @Test
    public void changeMoney() {
        Student student = new Student("201806061302", "陈力", clazzMapper.selectByName("计算机科学与技术学院实验班1801").get(0), "123456", 10000, "201806061302@zjut.edu.cn");
        studentMapper.changeMoney(student, 10000);
    }

    @Test
    public void selectByISBN() {


//        Student student = studentMapper.selectStudentById("201806061219");
//        System.out.println(student);
    }
}
