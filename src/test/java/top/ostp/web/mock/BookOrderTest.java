package top.ostp.web.mock;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.controller.BookController;
import top.ostp.web.mapper.BookOrderMapper;
import top.ostp.web.mapper.ClazzMapper;
import top.ostp.web.model.Clazz;
import top.ostp.web.service.BookOrderService;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookOrderTest {
    BookController bookController;
    BookOrderService bookOrderService;

    @Autowired
    public void setBookController(BookController bookController) {
        this.bookController = bookController;
    }

    @Autowired
    public void setBookOrderService(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    /**
     *
     */
    @Test
    void testGetBookOrderListByClazz() {
        int clazzId = 1234;
        BookOrderMapper bookOrderMapper = mock(BookOrderMapper.class);
        ClazzMapper clazzMapper = mock(ClazzMapper.class);
        Clazz clazz = new Clazz();
        clazz.setId(clazzId);
        when(clazzMapper.selectById(clazzId)).thenReturn(clazz);
        when(bookOrderMapper.selectByYearSemesterAndClazz());
    }
}
