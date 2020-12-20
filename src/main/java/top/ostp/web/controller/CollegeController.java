package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.College;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.CollegeService;

@Controller(value = "/college")
public class CollegeController {
    CollegeService collegeService;

    @Autowired
    public void setCollegeService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @AuthAdmin
    @PostMapping(value = "/college/insert")
    @ResponseBody
    public ApiResponse<Object> insertCollege(College college) {
        return collegeService.insert(college);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/college/select")
    @ResponseBody
    public ApiResponse<College> selectCollege(String name) {
        return collegeService.selectByName(name);
    }

    @AuthAdmin
    @PostMapping(value = "college/delete")
    @ResponseBody
    public ApiResponse<Object> deleteCollege(int id) {
        return collegeService.deleteById(id);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "college/list")
    @ResponseBody
    public ApiResponse<Object> selectAllCollege() {
        return collegeService.selectAll();
    }
}
