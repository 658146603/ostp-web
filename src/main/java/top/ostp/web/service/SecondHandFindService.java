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
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;
import java.util.UUID;

@Service
public class SecondHandFindService {
    SecondHandFindMapper secondHandFindMapper;
    SecondHandPublishMapper secondHandPublishMapper;
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
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper) {
        this.secondHandPublishMapper = secondHandPublishMapper;
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

    public ApiResponse<Object> insert(String person, String book, int price, int exchange, int status) {
        try {
            String id = UUID.randomUUID().toString();
            Student student = studentMapper.selectStudentById(person);
            Book book1 = bookMapper.selectByISBN(book);
            if (student == null || book1 == null) {
                return Responses.fail("参数错误");
            }
            SecondHandFind secondHandFind = new SecondHandFind(id, student, book1, price, exchange, status);

            if (secondHandFindMapper.insert(secondHandFind) > 0) {
                return Responses.ok("添加成功");
            } else {
                return Responses.fail("添加失败");
            }

        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return Responses.fail("主键重复");
        }
    }

    public ApiResponse<Object> select(String id) {
        SecondHandFind secondHandFind = secondHandFindMapper.select(id);
        if (secondHandFind != null) {
            return Responses.ok(secondHandFind);
        } else {
            return Responses.fail("未找到该记录");
        }
    }

    public ApiResponse<List<SecondHandFind>> selectByBook(String isbn) {
        List<SecondHandFind> secondHandFind = secondHandFindMapper.selectByBook(isbn);
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
        if (secondHandFind == null) {
            return Responses.fail("没有该发布的书本");
        }
        if (!student.getId().equals(secondHandFind.getPerson().getId())) {
            return Responses.fail("不能修改其他用户的数据");
        }
        if (secondHandFind.getStatus() != 0) {
            return Responses.fail("不能取消已完成的订单");
        }
        int result = secondHandFindMapper.delete(secondHandFind);
        if (result > 0) {
            return Responses.ok();
        } else {
            return Responses.fail("数据库修改失败");
        }
    }

    public ApiResponse<Object> changeStatusOk(String id, String studentId) {
        Student student = studentMapper.selectStudentById(studentId);
        SecondHandFind secondHandFind = secondHandFindMapper.select(id);
        if (secondHandFind == null) {
            return Responses.fail("没有该发布的书本");
        }
        if (!student.getId().equals(secondHandFind.getPerson().getId())) {
            return Responses.fail("不能修改其他用户的数据");
        }
        if (secondHandFind.getStatus() == 0) {
            return Responses.fail("不能修改未完成的订单");
        }

        secondHandFind.setStatus(2);
        if (secondHandFind.getExchange() == 0) {
            // 购买则卖方加钱
            Student other = secondHandFind.getSecond().getPerson();
            studentMapper.changeMoney(other, (int) secondHandFind.getPrice());
        }

        secondHandFindMapper.update(secondHandFind);
        return Responses.ok(secondHandFindMapper.select(id));
    }


    public ApiResponse<List<SecondHandFind>> selectByStudentIdNotExchanged(String otherId, String selfId) {
        Student other = studentMapper.selectStudentById(otherId);
        Student self = studentMapper.selectStudentById(selfId);
        if (self == null || other == null) {
            return Responses.fail("用户不存在");
        }
        return Responses.ok(secondHandFindMapper.selectByStudentIdNotExchanged(otherId, selfId));
    }

    public ApiResponse<Object> postExchange(String otherId, String selfId, String otherFindId, String otherPublishId) {
        Student other = studentMapper.selectStudentById(otherId);
        Student self = studentMapper.selectStudentById(selfId);
        SecondHandPublish otherPublish = secondHandPublishMapper.select(otherPublishId);
        SecondHandFind otherFind = secondHandFindMapper.select(otherFindId);
        if (other != null && self != null && otherPublish != null && otherFind != null && otherFind.getStatus() == 0 && otherPublish.getStatus() == 0) {
            SecondHandPublish selfPublish = secondHandPublishMapper.selectPublishByStudentAndISBNAndAvailable(selfId, otherFind.getBook().getIsbn());
            if (selfPublish == null) {
                //找不到发布订单则新建一个发布订单
                selfPublish = new SecondHandPublish();
                selfPublish.setId(UUID.randomUUID().toString());
                selfPublish.setPerson(self);
                selfPublish.setPrice(0);
                selfPublish.setExchange(1);
                selfPublish.setBook(otherFind.getBook());
                selfPublish.setSecond(otherFind);
                selfPublish.setStatus(1);
                secondHandPublishMapper.insertAll(selfPublish);
            } else {
                selfPublish.setSecond(otherFind);
                selfPublish.setStatus(1);
                secondHandPublishMapper.update(selfPublish);
            }

            otherFind.setSecond(selfPublish);
            otherFind.setStatus(1);
            secondHandFindMapper.update(otherFind);

            SecondHandFind selfFind = secondHandFindMapper.selectFindByStudentAndBookAndAvailable(selfId, otherPublish.getBook().getIsbn());
            if (selfFind == null) {
                selfFind = new SecondHandFind();
                selfFind.setId(UUID.randomUUID().toString());
                selfFind.setPerson(self);
                selfFind.setPrice(0);
                selfFind.setExchange(1);
                selfFind.setBook(otherPublish.getBook());
                selfFind.setSecond(otherPublish);
                selfFind.setStatus(1);
                secondHandFindMapper.insertAll(selfFind);
            } else {
                selfFind.setSecond(otherPublish);
                selfFind.setStatus(1);
                secondHandFindMapper.update(selfFind);
            }

            otherPublish.setSecond(selfFind);
            otherPublish.setStatus(1);
            secondHandPublishMapper.update(otherPublish);

            return Responses.ok("交换发起成功");
        } else {
            return Responses.fail("对方订单失效，请稍后重试");
        }
    }
}
