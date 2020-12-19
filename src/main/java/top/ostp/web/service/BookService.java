package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;

@Service
public class BookService {
    BookMapper bookMapper;

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public ApiResponse<Object> insert(Book book) {
        try {
            int result = bookMapper.insert(book);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail();
    }

    public ApiResponse<Book> selectByISBN(String isbn) {
        Book book = bookMapper.selectByISBN(isbn);
        if (book != null) {
            return Responses.ok(book);
        } else {
            return Responses.fail("ISBN不存在");
        }
    }

    public ApiResponse<Object> deleteByISBN(String isbn) {
        int result = bookMapper.deleteByISBN(isbn);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("删除失败");
        }

    }

    public ApiResponse<Object> updateBook(Book book) {
        int result = bookMapper.updateByISBN(book);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("更新失败");
        }
    }

    public ApiResponse<List<Book>> fuzzyQuery(String name) {
        return Responses.ok(bookMapper.fuzzyQuery(name));
    }

    public ApiResponse<List<Book>> selectAll() {
        return Responses.ok(bookMapper.selectAll());
    }
}
