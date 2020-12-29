package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.mapper.SecondHandFindMapper;
import top.ostp.web.mapper.SecondHandPublishMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.annotations.Blame;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecondHandPublishService {
    SecondHandPublishMapper secondHandPublishMapper;
    SecondHandFindMapper secondHandFindMapper;
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

    @Autowired
    public void setSecondHandFindMapper(SecondHandFindMapper secondHandFindMapper) {
        this.secondHandFindMapper = secondHandFindMapper;
    }

    public ApiResponse<Object> insert(String person, String book, int price, int exchange) {
        try {
            String id = UUID.randomUUID().toString();
            Student student = studentMapper.selectStudentById(person);
            Book book1 = bookMapper.selectByISBN(book);

            if (student == null || book1 == null) {
                return Responses.fail("参数错误");
            }
            SecondHandPublish secondHandPublish = new SecondHandPublish(id, student, book1, price, exchange, 0);
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
        int result = secondHandPublishMapper.delete(id);
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

    public ApiResponse<List<SecondHandPublish>> searchBuyList(String name, String studentId, String publisher) {
        return Responses.ok(secondHandPublishMapper.selectBuyListByNamePublisherExceptStudent(name, studentId, publisher));
    }

    public ApiResponse<List<SecondHandPublish>> searchExchangeList(String name, String studentId, String publisher) {
        return Responses.ok(secondHandPublishMapper.selectExchangeListByNamePublisherExceptStudent(name, studentId, publisher));
    }

    public ApiResponse<SecondHandPublish> selectPublishByOrderId(String id) {
        return Responses.ok(secondHandPublishMapper.select(id));
    }

    public ApiResponse<List<SecondHandPublish>> selectPublishByISBN(String isbn) {
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

    public ApiResponse<Object> purchase(String id, String studentId) {
        Student student = studentMapper.selectStudentById(studentId);
        SecondHandPublish secondHandPublish = secondHandPublishMapper.select(id);
        if (secondHandPublish == null) {
            return Responses.fail("没有该订单");
        }
        if (secondHandPublish.getPerson().getId().equals(studentId)) {
            return Responses.fail("你不能购买自己的书籍");
        } else if (secondHandPublish.getExchange() == 1) {
            return Responses.fail("该书籍无法购买");
        } else if (secondHandPublish.getStatus() == 1) {
            return Responses.fail("该书籍已被购买");
        }
        // do purchase operation
        // first check if there's available want list?
        Optional<SecondHandFind> wantList = secondHandFindMapper.selectBuyListByStudentAndBook(studentId, secondHandPublish.getBook().getIsbn()).stream().findAny();
        SecondHandFind secondHandFind = null;
        int result = 0;
        if (student.getBalance() < secondHandPublish.getPrice()) {
            return Responses.fail("余额不足");
        }

        if (wantList.isPresent()) {
            // if has one, change it
            secondHandFind = wantList.get();
            secondHandFind.setStatus(1);
            secondHandFind.setPrice(secondHandPublish.getPrice());
            secondHandFind.setSecond(secondHandPublish);
            result = secondHandFindMapper.update(secondHandFind);
        } else {
            // else create a secondHandFind
            secondHandFind = new SecondHandFind();
            secondHandFind.setId(UUID.randomUUID().toString());
            secondHandFind.setBook(secondHandPublish.getBook());
            secondHandFind.setExchange(0);
            secondHandFind.setStatus(1);
            secondHandFind.setPrice(secondHandPublish.getPrice());
            secondHandFind.setPerson(student);
            secondHandFind.setSecond(secondHandPublish);
            result = secondHandFindMapper.insert(secondHandFind);

        }

        if (result > 0) {
            // 提交更改
            secondHandPublish.setStatus(1);
            secondHandPublish.setSecond(secondHandFind);
            secondHandPublishMapper.update(secondHandPublish);
            // 进行缴费
            studentMapper.changeMoney(student, - (int)secondHandPublish.getPrice());
            // 然后给另外一个人加钱
            // TODO: 仅在买方确认后才会加钱，请产品经理审阅。
            // studentMapper.changeMoney(secondHandPublish.getPerson(), (int)secondHandPublish.getPrice());
            // 进行链接


            // studentMapper.update(student); // TODO ??? 钱是在这里回去的
            return Responses.ok();
        } else {
            return Responses.fail("执行数据库操作失败");
        }


    }

    public ApiResponse<Object> cancel(String id, String studentId) {
        Student student = studentMapper.selectStudentById(studentId);
        SecondHandPublish secondHandPublish = secondHandPublishMapper.select(id);
        if (secondHandPublish == null) {
            return Responses.fail("没有该发布的书本");
        }
        if (!student.getId().equals(secondHandPublish.getPerson().getId())) {
            return Responses.fail("不能修改其他用户的数据");
        }
        if (secondHandPublish.getStatus() != 0) {
            return Responses.fail("不能取消已完成的订单");
        }
        int result = secondHandPublishMapper.delete(id);
        if (result > 0) {
            return Responses.ok();
        } else {
            return Responses.fail("数据库修改失败");
        }
    }

    public ApiResponse<Object> changeStatusOk(String id, String studentId) {
        Student student = studentMapper.selectStudentById(studentId);
        SecondHandPublish secondHandPublish = secondHandPublishMapper.select(id);
        if (secondHandPublish == null) {
            return Responses.fail("没有该发布的书本");
        }
        if (!student.getId().equals(secondHandPublish.getPerson().getId())) {
            return Responses.fail("不能修改其他用户的数据");
        }
        if (secondHandPublish.getStatus() == 0) {
            return Responses.fail("不能修改未完成的订单");
        }
        if (secondHandPublish.getStatus() == 1) {
            secondHandPublish.setStatus(2);
        } else {
            secondHandPublish.setStatus(1);
        }
        secondHandPublishMapper.update(secondHandPublish);
        return Responses.ok(secondHandPublishMapper.select(id));
    }
}
