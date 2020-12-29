package top.ostp.web.controller;

import kotlin.Pair;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.ostp.web.model.Admin;
import top.ostp.web.model.Book;
import top.ostp.web.model.Clazz;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.model.complex.BookAdvice;
import top.ostp.web.model.complex.SearchParams;
import top.ostp.web.model.complex.SearchParams2;
import top.ostp.web.service.BookOrderService;
import top.ostp.web.service.BookService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class BookController {
    BookService bookService;

    BookOrderService bookOrderService;


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setBookOrderService(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }


    @AuthAdmin
    @PostMapping(value = "/book/insert")
    @ResponseBody
    public ApiResponse<Object> insertBook(String isbn, Long price, String cover, String name) {
        Book book = new Book(isbn, name, price, cover);
        return bookService.insert(book);
    }

    /**
     * 获取学生的搜索列表
     * @param name 搜索字段：书本名称
     * @param course 搜索字段：课程名
     * @param year 搜索字段：开课学年
     * @param semester 搜索字段：开课学期
     * @param request 请求
     * @return 书籍的扩展数据
     */
    @AuthStudent
    @PostMapping(value = "/book/search_stu")
    @ResponseBody
    public ApiResponse<List<BookAdvice>> searchOfStudent(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String course,
            @RequestParam(defaultValue = "2020") int year,
            @RequestParam(defaultValue = "1") int semester,
            HttpServletRequest request) {
        String studentId = (String) request.getSession().getAttribute("username");
        SearchParams params = new SearchParams(studentId, name, course, year, semester);
        return Responses.ok(bookService.searchOfStudent(params));
    }

    /**
     * 获取教师的搜索列表
     * @param name 搜索字段：书本名称
     * @param course 搜索字段：课程名
     * @param year 搜索字段：开课学年
     * @param semester 搜索字段：开课学期
     * @param request 请求
     * @return 书籍的扩展数据
     */
    @AuthTeacher
    @PostMapping(value = "/book/search_teacher")
    @ResponseBody
    public ApiResponse<List<BookAdvice>> searchOfTeacher(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String course,
            @RequestParam(defaultValue = "2020") int year,
            @RequestParam(defaultValue = "1") int semester,
            HttpServletRequest request) {
        String teacherId = (String) request.getSession().getAttribute("username");
        SearchParams params = new SearchParams(teacherId, name, course, year, semester);
        return Responses.ok(bookService.searchOfTeacher(params));
    }

    /**
     * 获取管理员的搜索列表
     * @param name 搜索字段：书本名称
     * @param course 搜索字段：课程名
     * @param year 搜索字段：开课学年
     * @param semester 搜索字段：开课学期
     * @param request 请求
     * @return 书籍的扩展数据
     */
    @AuthAdmin
    @PostMapping(value = "/book/search_admin")
    @ResponseBody
    public ApiResponse<List<BookAdvice>> searchOfAdmin(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String course,
            @RequestParam(defaultValue = "2020") int year,
            @RequestParam(defaultValue = "1") int semester,
            HttpServletRequest request
    ) {
        Admin admin = (Admin) request.getSession().getAttribute("role");
        SearchParams params = new SearchParams(admin.getId(), name, course, year, semester);
        if (admin.getSu() > 0) {
            return Responses.ok(bookService.searchOfSuAdmin(params));
        } else {
            return Responses.ok(bookService.searchOfCollegeAdmin(params));
        }
    }

    /**
     * 学生订阅一本书
     * @param isbn 书籍编号
     * @param request 请求
     * @return 操作的结果
     */
    @AuthStudent
    @PostMapping(value = "/book/order_stu")
    @ResponseBody
    public ApiResponse<?> orderStudent(
            String isbn,
            @RequestParam(defaultValue = "2020") int year,
            @RequestParam(defaultValue = "1") int semester,
            HttpServletRequest request) {
        String studentId = (String) request.getSession().getAttribute("username");
        SearchParams2 searchParams2 = new SearchParams2(studentId,  isbn, year, semester);
        return bookService.orderBookStu(searchParams2);
    }

    /**
     * 教师领取一本书
     * @param isbn     书籍编号
     * @param year     学年
     * @param semester 学期
     * @param request  请求
     * @return 操作的结果
     */
    @AuthTeacher
    @PostMapping(value = "/book/order_teacher")
    @ResponseBody
    public ApiResponse<?> orderTeacher(
            String isbn,
            @RequestParam(defaultValue = "2020") Integer year,
            @RequestParam(defaultValue = "1") Integer semester,
            HttpServletRequest request) {
        String teacherId = (String) request.getSession().getAttribute("username");
        SearchParams2 params2 = new SearchParams2(teacherId, isbn, year, semester);
        return bookService.orderBookTeacher(params2);
    }


    /**
     * 根据isbn查找一本书籍，用于查询框
     * @param isbn 编号
     * @return 查询的结果
     */
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

    // TODO: @NoUsed
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

    @AuthAdmin
    @RequestMapping("/book/order/export/{clazzId}")
    @ResponseBody
    public void exportBookOrderByClazz(@PathVariable int clazzId, boolean received, HttpServletResponse resp) {
        Pair<String, XSSFWorkbook> result = bookOrderService.getBookOrderListByClazz(clazzId, received);
        var filename = result.component1();
        resp.setHeader("Content-Disposition", filename);
        resp.setHeader("Connection", "close");
        resp.setHeader("Content-Type", "application/octet-stream");
        try (ServletOutputStream stream = resp.getOutputStream()) {
            result.component2().write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AuthAdmin
    @RequestMapping("/book/request/export/{collegeId}")
    @ResponseBody
    public void exportBookRequestByCollege(@PathVariable int collegeId, HttpServletResponse resp) {
        Pair<String, XSSFWorkbook> result = bookOrderService.getBookRequestListByCollege(collegeId);
        var filename = result.component1();
        resp.setHeader("Content-Disposition", filename);
        resp.setHeader("Connection", "close");
        resp.setHeader("Content-Type", "application/octet-stream");
        try (ServletOutputStream stream = resp.getOutputStream()) {
            result.component2().write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
