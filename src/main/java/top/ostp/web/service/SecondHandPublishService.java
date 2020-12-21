package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.controller.StudentController;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.mapper.SecondHandPublishMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;
import java.util.UUID;

@Service
public class SecondHandPublishService {
    SecondHandPublishMapper secondHandPublishMapper;
    StudentMapper studentMapper;
    BookMapper bookMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Autowired
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper) {
        this.secondHandPublishMapper = secondHandPublishMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public ApiResponse<Object> insert(String person, String book, int price, int exchange, int status) {
        try {
            String id = UUID.randomUUID().toString();
            Student student = studentMapper.selectStudentById(person);
            Book book1 = bookMapper.selectByISBN(book);

            if (student == null || book1 == null) {
                return Responses.fail("参数错误");
            }
            SecondHandPublish secondHandPublish = new SecondHandPublish(id, student, book1, price, exchange, status);
            int result = secondHandPublishMapper.insert(secondHandPublish);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<Object> deleteByOrderId(String id) {
//        SecondHandPublish = secondHandPublishMapper
//        Book book1 = bookMapper.selectByISBN(book);
        int result = secondHandPublishMapper.deleteByOrderId(id);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<List<SecondHandPublish>> selectAll() {
        return Responses.ok(secondHandPublishMapper.selectAll());
    }

    public ApiResponse<List<SecondHandPublish>> selectPublishByStudentId(String id) {
        return Responses.ok(secondHandPublishMapper.selectPublishByStudentId(id));
    }

    public ApiResponse<SecondHandPublish> selectPublishByOrderId(String id) {
        return Responses.ok(secondHandPublishMapper.selectPublishByOrderId(id));
    }

    public ApiResponse<List<SecondHandPublish>> selectPublishByISBN(String isbn){
        return Responses.ok(secondHandPublishMapper.selectPublishByISBN(isbn));
    }

    public ApiResponse<Object> update(String id, String person, String book, int price, int exchange, int status) {
        Student student = studentMapper.selectStudentById(person);
        Book book1 = bookMapper.selectByISBN(book);

        if (student == null || book1 == null) {
            return Responses.fail("参数错误");
        }
        SecondHandPublish secondHandPublish = new SecondHandPublish(id, student, book1, price, exchange, status);
        try {
            int result = secondHandPublishMapper.update(secondHandPublish);
            return Responses.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Responses.fail("更新失败");
    }
}
