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
import top.ostp.web.model.Student;
import top.ostp.web.model.StudentBookOrder;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.model.complex.BookAdvice;

import java.util.List;
import java.util.Objects;
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

    public List<BookAdvice> selectByQueryParameters(String name, String course) {
        if (name.equals("") && course.equals("")) {
            return bookMapper.selectAll().stream()
                    .map((book) -> selectXByISBN(book.getIsbn()))
                    .collect(Collectors.toList());
        } else if(course.equals("")) {
            return bookMapper.fuzzyQuery(name).stream()
                    .map((book) -> selectXByISBN(book.getIsbn()))
                    .collect(Collectors.toList());
        } else {
            return bookMapper.selectByQueryParameters(name, course).stream()
                    .map((book) -> selectXByISBN(book.getIsbn()))
                    .collect(Collectors.toList());
        }
    }

    public List<BookAdvice> searchOfStudent(String name, String course, String studentId) {
        if (name.equals("") && course.equals("")) {
            return bookMapper.selectAll().stream()
                    .map((book) -> selectXByISBNOfStudent(book.getIsbn(), studentId))
                    .collect(Collectors.toList());
        } else if(course.equals("")) {
            return bookMapper.fuzzyQuery(name).stream()
                    .map((book) -> selectXByISBNOfStudent(book.getIsbn(), studentId))
                    .collect(Collectors.toList());
        } else {
            return bookMapper.selectByQueryParameters(name, course).stream()
                    .map((book) -> selectXByISBNOfStudent(book.getIsbn(), studentId))
                    .collect(Collectors.toList());
        }
    }

    public BookAdvice selectXByISBNOfStudent(String isbn, String studentId) {
        BookAdvice bookAdvice = selectXByISBN(isbn);
        Student student = studentMapper.selectStudentById(studentId);
        if (!bookAdvice.getCourseOpens().isEmpty()){
            bookAdvice.setCourseOpens(bookAdvice.getCourseOpens().stream().filter(
                    courseOpen -> courseOpen.getCourse().getMajor().getId() == student.getClazz().getMajor().getId())
                    .collect(Collectors.toList()));
        }
        if (!bookAdvice.getOrders().isEmpty()) {
            bookAdvice.setOrders(bookAdvice.getOrders().stream().filter(
                    order-> order.getStudent().getId().equals(studentId))
                    .collect(Collectors.toList()));
        }
        return bookAdvice;
    }

    public BookAdvice selectXByISBN(String isbn) {
        Book book = bookMapper.selectByISBN(isbn);
        if (book == null){
            return null;
        } else {
            return new BookAdvice(book, courseOpenMapper.selectByBook(book), bookOrderMapper.selectByBook(book));
        }
    }

    public ApiResponse<Object> orderBookStu(String isbn, String studentId){
        // 获取增强模型
        BookAdvice bookAdvice = selectXByISBNOfStudent(isbn, studentId);
        if (bookAdvice.getCourseOpens().isEmpty()) {
            return Responses.fail("你无法订阅这本书，英文没有相关的开课");
        } else {
            CourseOpen courseOpen = bookAdvice.getCourseOpens().get(0);
            Optional<StudentBookOrder> order = bookAdvice.getOrders().stream().filter((o) -> o.getStudent().getId().equals(studentId)).findAny();
            if (order.isEmpty()) {
                bookOrderMapper.addBookOrder(studentId, bookAdvice.getBook().getIsbn(), bookAdvice.getBook().getPrice().intValue(), courseOpen.getYear(), courseOpen.getSemester() );
            } else {
                StudentBookOrder studentBookOrder = bookOrderMapper.selectByBookAndStudent(bookAdvice.getBook().getIsbn(), studentId);
                if (studentBookOrder != null){
                    bookOrderMapper.deleteBookOrder((int) studentBookOrder.getId());
                }
            }
            return Responses.ok("修改成功");
        }
    }
}
