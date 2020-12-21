package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.AdminMapper;
import top.ostp.web.model.Admin;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

@Service
public class AdminService {
    AdminMapper adminMapper;

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public ApiResponse<Object> insert(Admin admin) {
        admin.setPassword(EncryptProvider.getSaltedPassword(admin.getId(), admin.getPassword()));
        try {
            adminMapper.insert(admin);
            return Responses.ok("插入成功");
        } catch (DuplicateKeyException e) {
            return Responses.fail("插入失败");
        }
    }

    public ApiResponse<Object> delete(Admin admin) {
        if (adminMapper.delete(admin) == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> update(Admin admin) {
        if (adminMapper.update(admin) == 1) {
            return Responses.ok("更新成功");
        } else {
            return Responses.fail("更新失败");
        }
    }

    public ApiResponse<Object> select(String id) {
        Admin admin = adminMapper.select(id);
        if (admin != null) {
            return Responses.ok(admin.erasePassword());
        } else {
            return Responses.fail("未找到");
        }
    }
}
