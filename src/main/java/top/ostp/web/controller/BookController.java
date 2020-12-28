package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.model.complex.BookAdvice;
import top.ostp.web.service.BookService;

import javax.servlet.http.HttpServletRequest;
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

    @AuthStudent
    @PostMapping(value = "/book/search_stu")
    @ResponseBody
    public ApiResponse<List<BookAdvice>> searchOfStudent(@RequestParam(defaultValue = "") String name,@RequestParam(defaultValue = "") String course, HttpServletRequest request) {
        String studentId = (String) request.getSession().getAttribute("username");
        return Responses.ok(bookService.searchOfStudent(name, course, studentId));
    }

    @AuthTeacher
    @PostMapping(value = "/book/search_teacher")
    @ResponseBody
    public ApiResponse<List<BookAdvice>> searchOfTeacher(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String course, HttpServletRequest request) {
        String teacherId = (String) request.getSession().getAttribute("username");
        return Responses.ok(bookService.searchOfTeacher(name, course, teacherId));
    }

    /**
     * 学生订阅一本书
     * TODO: 能够区分学年和学期 @@Alert
     * @param isbn 书籍编号
     * @param request 请求
     * @return 操作的结果
     */
    @AuthStudent
    @PostMapping(value = "/book/order_stu")
    @ResponseBody
    public ApiResponse<Object> orderStudent(String isbn, HttpServletRequest request) {
        String studentId = (String) request.getSession().getAttribute("username");
        return bookService.orderBookStu(isbn, studentId);
    }

    /**
     * 教师订阅一本书
     * @param isbn 书籍编号
     * @param year 学年
     * @param semester 学期
     * @param request 请求
     * @return 操作的结果
     */
    @AuthTeacher
    @PostMapping(value = "/book/order_teacher")
    @ResponseBody
    public ApiResponse<?> orderTeacher(String isbn, Integer year, Integer semester, HttpServletRequest request) {
        String teacherId = (String) request.getSession().getAttribute("username");
        boolean result = bookService.orderBookTeacher(isbn, year, semester, teacherId);
        if (result) {
            return Responses.ok();
        } else {
            return Responses.fail();
        }
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
