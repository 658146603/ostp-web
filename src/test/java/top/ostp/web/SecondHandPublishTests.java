package top.ostp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.mapper.SecondHandPublishMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.service.SecondHandPublishService;

import java.util.List;

@SpringBootTest
public class SecondHandPublishTests {
    SecondHandPublishMapper secondHandPublishMapper;
    StudentMapper studentMapper;
    BookMapper bookMapper;
    @Autowired
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper){
        this.secondHandPublishMapper = secondHandPublishMapper;
    }
    @Autowired
    public void setStudentMapper(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }
    @Autowired
    public void setBookMapper(BookMapper bookMapper){
        this.bookMapper = bookMapper;
    }
    @Test
    public void selectAll(){
        List<SecondHandPublish> secondHandPublishes = secondHandPublishMapper.selectAll();
        for (SecondHandPublish s:secondHandPublishes) {
            System.out.println(s);

        }
    }
    @Test
    public void selectPublishByStudentId(){
        Student s = new Student();
        s.setId("201806060231");
        List<SecondHandPublish> secondHandPublishes = secondHandPublishMapper.selectPublishByStudentId("201806061108");
        for (SecondHandPublish s1:secondHandPublishes) {
            System.out.println(s1.toString());
        }
    }

}
