package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ostp.web.model.Admin;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.AdminService;

import java.util.ArrayList;

@NoAuthority
@RestController
public class AdminController {
    AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/admin/insert")
    @ResponseBody
    public ApiResponse<Object> insert(Admin admin) {
        return adminService.insert(admin);
    }

    @PostMapping(value = "/admin/select")
    @ResponseBody
    public ApiResponse<Object> select(String id) {
        return adminService.select(id);
    }

    @PostMapping(value = "/admin/update")
    @ResponseBody
    public ApiResponse<Object> update(Admin admin) {
        return adminService.update(admin);
    }

    @PostMapping(value = "/admin/delete")
    @ResponseBody
    public ApiResponse<Object> delete(Admin admin) {
        return adminService.delete(admin);
    }
}
