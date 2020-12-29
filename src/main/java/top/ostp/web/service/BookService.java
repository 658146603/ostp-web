package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.mapper.BookOrderMapper;
import top.ostp.web.mapper.CourseOpenMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.CourseOpen;
import top.ostp.web.model.StudentBookOrder;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.model.complex.BookAdvice;
import top.ostp.web.model.complex.SearchParams;
import top.ostp.web.model.complex.SearchParams2;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    BookMapper bookMapper;
    CourseOpenMapper courseOpenMapper;
    BookOrderMapper bookOrderMapper;
    StudentMapper studentMapper;

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Autowired
    public void setCourseOpenMapper(CourseOpenMapper courseOpenMapper){
        this.courseOpenMapper = courseOpenMapper;
    }

    @Autowired
    public void setBookOrderMapper(BookOrderMapper bookOrderMapper){
        this.bookOrderMapper = bookOrderMapper;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
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

    public List<BookAdvice> searchOfStudent(SearchParams params) {
        return bookMapper.searchOfStudent(params)
                .stream().map((book)-> selectXByISBNOfStudent(params.toSearchParams2(book))).collect(Collectors.toList());
    }

    public BookAdvice selectXByISBNOfStudent(SearchParams2 params2) {
        Book book = bookMapper.selectByISBN(params2.getIsbn());
        if (book == null) {
            return null;
        } else {
            return new BookAdvice(book, courseOpenMapper.searchOfStudent(params2), bookOrderMapper.searchOfStudent(params2), false);
        }
    }

    public List<BookAdvice> searchOfTeacher(String name, String course, String teacherId) {
        return bookMapper.selectByTeacherNameAndCourse(teacherId, name, course)
                .stream().map((book)-> selectXByISBNOfTeacher(book, teacherId)).collect(Collectors.toList());
    }

    public BookAdvice selectXByISBNOfTeacher(Book book, String teacherId){
        return new BookAdvice(book, courseOpenMapper.selectByBookAndTeacher(book.getIsbn(), teacherId), new LinkedList<>(), false);
    }

    public ApiResponse<Object> orderBookStu(SearchParams2 params2){
        // 获取增强模型
        BookAdvice bookAdvice = selectXByISBNOfStudent(params2);
        if (bookAdvice == null) {
            return Responses.fail("没有这本书");
        }
        if (bookAdvice.getCourseOpens().isEmpty()) {
            return Responses.fail("你无法订阅这本书，因为没有相关的开课");
        } else {
            Optional<StudentBookOrder> order = bookAdvice.getOrders().stream().findAny();
            if (order.isEmpty()) {
                bookOrderMapper.addBookOrder(params2.getPersonId(), params2.getIsbn(), bookAdvice.getBook().getPrice().intValue(), params2.getYear() , params2.getSemester());
            } else {
                bookOrderMapper.deleteBookOrder((int)order.get().getId());
            }
            return Responses.ok("修改成功");
        }
    }

    public ApiResponse<List<StudentBookOrder>> orderListByStudentYearSemester(String student, int year, int semester) {
        if (student == null) {
            return Responses.fail(new ArrayList<>());
        }
        return Responses.ok(bookOrderMapper.selectByYearSemesterAndStudent(student, year, semester));
    }

    public boolean orderBookTeacher(String isbn, Integer year, Integer semester, String teacherId) {
        Book book = bookMapper.selectByISBN(isbn);
        BookAdvice bookAdvice = selectXByISBNOfTeacher(book, teacherId);
        return false;
    }
}
