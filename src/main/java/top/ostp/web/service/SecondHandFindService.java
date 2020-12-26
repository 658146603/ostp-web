package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.mapper.SecondHandFindMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;
import java.util.UUID;

@Service
public class SecondHandFindService {
    SecondHandFindMapper secondHandFindMapper;
    StudentMapper studentMapper;
    BookMapper bookMapper;
    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }


    @Autowired
    public void setSecondHandFindMapper(SecondHandFindMapper secondHandFindMapper) {
        this.secondHandFindMapper = secondHandFindMapper;
    }

    public ApiResponse<Object> selectAll() {
        List<SecondHandFind> secondHandFinds = secondHandFindMapper.selectAll();
        if (secondHandFinds.isEmpty()) {
            return Responses.fail("未找到记录");
        } else {
            return Responses.ok(secondHandFinds);
        }
    }

    public ApiResponse<Object> insert(String person,String book,int price,int exchange,int status){
        try {
            String id = UUID.randomUUID().toString();
            Student student = studentMapper.selectStudentById(person);
            Book book1= bookMapper.selectByISBN(book);
            if(student == null || book1 == null){
                return Responses.fail("参数错误");
            }
            SecondHandFind secondHandFind = new SecondHandFind(id,student,book1,price,exchange,status);
            int result = secondHandFindMapper.insert(secondHandFind);
            return Responses.ok();

        }catch (DuplicateKeyException e){
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<Object> select(String id) {
        SecondHandFind secondHandFind = secondHandFindMapper.select(id);
        if (secondHandFind != null) {
            return Responses.ok(secondHandFind);
        } else {
            return Responses.fail("未找到该记录");
        }
    }

    public ApiResponse<Object> selectByBook(String isbn) {
        SecondHandFind secondHandFind = secondHandFindMapper.selectByBook(isbn);
        if (secondHandFind != null) {
            return Responses.ok(secondHandFind);
        } else {
            return Responses.fail("未找到该记录");
        }
    }



    public ApiResponse<List<SecondHandFind>> selectByStudentId(String id) {
        return Responses.ok(secondHandFindMapper.selectByStudentId(id));
    }



    public ApiResponse<Object> insert(SecondHandFind secondHandFind) {
        try {
            secondHandFind.setId(UUID.randomUUID().toString());
            secondHandFindMapper.insert(secondHandFind);
            return Responses.ok("插入成功");
        } catch (DuplicateKeyException e) {
            return Responses.fail("插入失败");
        }
    }

    public ApiResponse<Object> delete(SecondHandFind secondHandFind) {
        int result = secondHandFindMapper.delete(secondHandFind);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> update(SecondHandFind secondHandFind) {
        int result = secondHandFindMapper.update(secondHandFind);
        if (result == 1) {
            return Responses.ok("更新成功");
        } else {
            return Responses.fail("更新失败");
        }
    }

    public ApiResponse<Object> cancel(String id, String studentId) {
        Student student = studentMapper.selectStudentById(studentId);
        SecondHandFind secondHandFind = secondHandFindMapper.select(id);
        if (secondHandFind == null){
            return Responses.fail("没有该发布的书本");
        }
        if (!student.getId().equals(secondHandFind.getPerson().getId())){
            return Responses.fail("不能修改其他用户的数据");
        }
        if (secondHandFind.getStatus() != 0) {
            return Responses.fail("不能取消已完成的订单");
        }
        int result = secondHandFindMapper.delete(secondHandFind);
        if (result > 0){
            return Responses.ok();
        } else {
            return Responses.fail("数据库修改失败");
        }
    }
}
