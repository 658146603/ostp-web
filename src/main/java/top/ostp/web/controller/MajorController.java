package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.Major;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.MajorService;

@Controller
public class MajorController {
    MajorService majorService;

    @Autowired
    public void setMajorService(MajorService majorService) {
        this.majorService = majorService;
    }

    @AuthAdmin
    @PostMapping(path = "/major/insert")
    @ResponseBody
    public ApiResponse<Object> insert(Major major) {
        return majorService.insert(major);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = {"/major/all", "/major/list"})
    @ResponseBody
    public ApiResponse<Object> selectAll() {
        return majorService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = {"/major/by/year"})
    @ResponseBody
    public ApiResponse<Object> selectByYear(int year) {
        return majorService.selectByYear(year);
    }

    @AuthAdmin
    @PostMapping(path = "/major/update")
    @ResponseBody
    public ApiResponse<Object> update(Major major) {
        return majorService.update(major);
    }

    @AuthAdmin
    @PostMapping(path = "/major/delete")
    @ResponseBody
    public ApiResponse<Object> delete(Major major) {
        return majorService.delete(major);
    }
}
