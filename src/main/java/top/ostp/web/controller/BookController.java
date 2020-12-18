package top.ostp.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.mapper.BookMapper;
import top.ostp.web.model.Book;


@Controller(value = "/book")
public class BookController {
    BookMapper bookMapper;

    static class Result {

        public Result(STATUS status, Object obj) {
            this.status = status;
            this.obj = obj;
        }

        enum STATUS {
            OK, FAIL,
            ;
        }

        STATUS status = STATUS.OK;
        Object obj;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @PostMapping(value = "/book/insert")
    @ResponseBody
    public String insertBook(Book book) {
        try {
            int result = bookMapper.insert(book);
            return new Gson().toJson(new Result(Result.STATUS.OK, result));
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(new Result(Result.STATUS.FAIL, null));
    }
}
