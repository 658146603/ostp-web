package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.service.BookService;

import java.util.List;


@Controller(value = "/book")
public class BookController {
    BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @NoAuthority
    //TODO  测试完删除
    @AuthAdmin
    @PostMapping(value = "/book/insert")
    @ResponseBody
    public ApiResponse<Object> insertBook(String isbn,Long price,String cover,String name) {
        Book book = new Book(isbn,name,price,cover);

        return bookService.insert(book);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/book/select")
    @ResponseBody
    public ApiResponse<Book> selectBook(String isbn) {
        return bookService.selectByISBN(isbn);
    }

    @AuthAdmin
    @PostMapping("/book/delete")
    @ResponseBody
    public ApiResponse<Object> deleteBook(String isbn) {
        return bookService.deleteByISBN(isbn);
    }

    @AuthAdmin
    @PostMapping("/book/update")
    @ResponseBody
    public ApiResponse<Object> updateBook(Book book) {
        return bookService.updateBook(book);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/book/list")
    @ResponseBody
    public ApiResponse<List<Book>> selectAll() {
        return bookService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping("/book/fuzzy")
    @ResponseBody
    public ApiResponse<List<Book>> fuzzyQuery(String name) {
        return bookService.fuzzyQuery(name);
    }
}
