package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.SecondHandPublishService;

import java.util.List;

@Controller(value = "/secondHandPublish")
public class SecondHandPublishController {
    SecondHandPublishService secondHandPublishService;

    @Autowired
    public void setSecondHandPublishService(SecondHandPublishService secondHandPublishService) {
        this.secondHandPublishService = secondHandPublishService;
    }

    @PostMapping(value = "/secondHandPublish/insert")
    @ResponseBody
    public ApiResponse<Object> insertSecondHandPublish(SecondHandPublish secondHandPublish) {
        return secondHandPublishService.insert(secondHandPublish);
    }

    @PostMapping(value = "/secondHandPublish/delete")
    @ResponseBody
    public ApiResponse<Object> deleteSecondHandPublish(Book book) {
        return secondHandPublishService.deleteByBookISBN(book);
    }
    @PostMapping(value = "/secondHandPublish/update")
    @ResponseBody
    public ApiResponse<Object> updateSecondHandPublish(SecondHandPublish secondHandPublish){
        return secondHandPublishService.update(secondHandPublish);
    }

    @PostMapping(value = "/secondHandPublish/selectAll")
    @ResponseBody
    public ApiResponse<List<SecondHandPublish>> selectAllCollege() {
        return secondHandPublishService.selectAll();
    }

    @PostMapping(value = "/secondHandPublish/select")
    @ResponseBody
    public  ApiResponse<List<SecondHandPublish>> selectSecondHandPublish(Student student){
        return secondHandPublishService.selectPublishByStudentId(student);
    }
}
