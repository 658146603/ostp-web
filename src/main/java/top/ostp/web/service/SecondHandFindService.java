package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.SecondHandFindMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SecondHandFindService {
    SecondHandFindMapper secondHandFindMapper;

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

    public ApiResponse<Object> selectByStudentId(String id) {
        List<SecondHandFind> bookList = secondHandFindMapper.selectBookByStudentId(id);
        List<Book> b1 = new ArrayList<>();
        for (SecondHandFind se : bookList) {
            b1.add(se.getBook());
        }
        if (bookList.size() > 0) {
            return Responses.ok(b1);
        } else {
            return Responses.fail("未找到该学生订阅的书籍");
        }
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
}
