package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.SecondHandPublishMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;

@Service
public class SecondHandPublishService {
    SecondHandPublishMapper secondHandPublishMapper;

    @Autowired
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper) {
        this.secondHandPublishMapper = secondHandPublishMapper;
    }

    public ApiResponse<Object> insert(SecondHandPublish secondHandPublish) {
        try {
            int result = secondHandPublishMapper.insert(secondHandPublish);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<Object> deleteByBookISBN(Book book) {
        int result = secondHandPublishMapper.deleteByBookISBN(book);
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

    public ApiResponse<Object> update(SecondHandPublish secondHandPublish) {
        try {
            int result = secondHandPublishMapper.update(secondHandPublish);
            return Responses.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Responses.fail("更新失败");
    }
}
