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

    public ApiResponse<Object> insert(String id, String password, String college) {
        password = EncryptProvider.getSaltedPassword(id, password);
        try {
            adminMapper.insert(id, password, college);
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

    public ApiResponse<Object> updatePassword(Admin admin, String password) {
        admin.setPassword(EncryptProvider.getSaltedPassword(admin.getId(), admin.getPassword()));
        password = EncryptProvider.getSaltedPassword(admin.getId(), password);
        int status = adminMapper.updatePassword(admin, password);
        if (status == 1) {
            return Responses.ok("密码修改成功");
        } else {
            return Responses.fail("错误");
        }
    }
}
