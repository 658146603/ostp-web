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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    public List<BookAdvice> searchOfStudent(String name, String course, String studentId) {
        return bookMapper.selectByStudentNameAndCourse(studentId, name, course)
                .stream().map((book)-> selectXByISBNOfStudent(book, studentId)).collect(Collectors.toList());
    }

    public BookAdvice selectXByISBNOfStudent(Book book, String studentId) {
        return new BookAdvice(book, courseOpenMapper.selectByBookAndStudent(book.getIsbn(), studentId), bookOrderMapper.selectByBookAndStudent(book.getIsbn(), studentId));
    }

    public List<BookAdvice> searchOfTeacher(String name, String course, String teacherId) {
        return bookMapper.selectByTeacherNameAndCourse(teacherId, name, course)
                .stream().map((book)-> selectXByISBNOfTeacher(book, teacherId)).collect(Collectors.toList());
    }

    public BookAdvice selectXByISBNOfTeacher(Book book, String teacherId){
        return new BookAdvice(book, courseOpenMapper.selectByBookAndTeacher(book.getIsbn(), teacherId), new LinkedList<>());
    }

    public ApiResponse<Object> orderBookStu(String isbn, String studentId){
        // 获取增强模型
        Book book = bookMapper.selectByISBN(isbn);
        BookAdvice bookAdvice = selectXByISBNOfStudent(book, studentId);
        if (bookAdvice.getCourseOpens().isEmpty()) {
            return Responses.fail("你无法订阅这本书，因为没有相关的开课");
        } else {
            CourseOpen courseOpen = bookAdvice.getCourseOpens().get(0);
            Optional<StudentBookOrder> order = bookAdvice.getOrders().stream().filter((o) -> o.getStudent().getId().equals(studentId)).findAny();
            if (order.isEmpty()) {
                bookOrderMapper.addBookOrder(studentId, bookAdvice.getBook().getIsbn(), bookAdvice.getBook().getPrice().intValue(), courseOpen.getYear(), courseOpen.getSemester() );
            } else {
                Optional<StudentBookOrder> studentBookOrder = bookOrderMapper.selectByBookAndStudent(bookAdvice.getBook().getIsbn(), studentId).stream().findAny();
                studentBookOrder.ifPresent(bookOrder -> bookOrderMapper.deleteBookOrder((int)bookOrder.getId()));
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
}
