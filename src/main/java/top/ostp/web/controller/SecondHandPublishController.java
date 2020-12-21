package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.Student;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
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

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/publish/insert")
    @ResponseBody
    public ApiResponse<Object> insertSecondHandPublish(@RequestParam("person") String person, @RequestParam("book") String book, int price, int exchange, int status) {
        return secondHandPublishService.insert(person, book, price, exchange, status);
    }

    @AuthAdmin
    @PostMapping(value = "/second/publish/delete")
    @ResponseBody
    public ApiResponse<Object> deleteSecondHandPublish(String id) {
        return secondHandPublishService.deleteByOrderId(id);
    }

    @AuthAdmin
    @PostMapping(value = "/second/publish/update")
    @ResponseBody
    public ApiResponse<Object> updateSecondHandPublish(String id, @RequestParam("person") String person, @RequestParam("book") String book, int price, int exchange, int status) {
        return secondHandPublishService.update(id, person, book, price, exchange, status);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/publish/select/all")
    @ResponseBody
    public ApiResponse<List<SecondHandPublish>> selectAllCollege() {
        return secondHandPublishService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/publish/select/{id}")
    @ResponseBody
    public ApiResponse<SecondHandPublish> selectSecondHandPublishByOrderId(@PathVariable String id) {
        return secondHandPublishService.selectPublishByOrderId(id);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/publish/selectByISBN")
    @ResponseBody
    public  ApiResponse<List<SecondHandPublish>> selectSecondHanPublishByISBN(String isbn){
        return secondHandPublishService.selectPublishByISBN(isbn);
    }
    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/publish/selectByStudent")
    @ResponseBody
    public ApiResponse<List<SecondHandPublish>> selectSecondHandPublishByStudentId(String id) {
        return secondHandPublishService.selectPublishByStudentId(id);
    }
}
