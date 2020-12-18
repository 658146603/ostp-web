package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.service.BookService;


@Controller(value = "/book")
public class BookController {
    BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/book/insert")
    @ResponseBody
    public ApiResponse<Object> insertBook(Book book) {
        return bookService.insert(book);
    }

    @PostMapping("/book/select")
    @ResponseBody
    public ApiResponse<Book> selectBook(String isbn) {
        return bookService.selectByISBN(isbn);
    }

    @PostMapping("/book/delete")
    @ResponseBody
    public ApiResponse<Object> deleteBook(String isbn) {
        return bookService.deleteByISBN(isbn);
    }

    @PostMapping("/book/update")
    @ResponseBody
    public ApiResponse<Object> updateBook(Book book) {
        return bookService.updateBook(book);
    }
}
