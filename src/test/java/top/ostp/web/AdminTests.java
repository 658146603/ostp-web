package top.ostp.web;

import net.minidev.json.writer.CollectionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.mapper.AdminMapper;
import top.ostp.web.mapper.CollegeMapper;
import top.ostp.web.model.Admin;
import top.ostp.web.model.College;
import top.ostp.web.util.EncryptProvider;

import java.util.Collection;

@SpringBootTest
public class AdminTests {
    AdminMapper adminMapper;
    CollegeMapper collegeMapper;

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Autowired
    public void setCollegeMapper(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
    }

    @Test
    void insert() {
        Admin admin = new Admin("0001", "12345", 0, collegeMapper.selectByName("计算机科学与技术学院"));
        assert adminMapper.insert(admin) == 1;
    }

    @Test
    void login() {
        Admin login = adminMapper.login("0001", "12345");
        assert login != null;
    }

    @Test
    void update() {
        Admin login = adminMapper.login("0001", "12345");
        login.setPassword("1234");
        assert adminMapper.update(login) == 1;
    }

    @Test
    void delete() {
        Admin admin = adminMapper.login("0001", "1234");
        assert adminMapper.delete(admin) == 1;
    }

    @Test
    void select() {
        Admin admin = adminMapper.select("0001");
        assert admin != null;
    }

    @Test
    void password() {
        System.out.println(EncryptProvider.getSaltedPassword("0000", "123456"));
        System.out.println(EncryptProvider.getSaltedPassword("0001", "123456"));
        System.out.println(EncryptProvider.getSaltedPassword("0002", "123456"));
        System.out.println(EncryptProvider.getSaltedPassword("0003", "123456"));
    }
}
