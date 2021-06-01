package top.ostp.web.mock;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.mapper.AdminMapper;
import top.ostp.web.mapper.StudentMapper;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Admin;
import top.ostp.web.model.Student;
import top.ostp.web.model.Teacher;
import top.ostp.web.service.LoginService;
import top.ostp.web.util.EncryptProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 登录测试包含4个等价类(学生/教师/管理员/错误的用户名或密码)
 */
@SpringBootTest
public class LoginTest {
    private LoginService loginService;
    private final StudentMapper studentMapper = EasyMock.createMock(StudentMapper.class);
    private final TeacherMapper teacherMapper = EasyMock.createMock(TeacherMapper.class);
    private final AdminMapper adminMapper = EasyMock.createMock(AdminMapper.class);

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
        this.loginService.setStudentMapper(studentMapper);
        this.loginService.setTeacherMapper(teacherMapper);
        this.loginService.setAdminMapper(adminMapper);
    }

    @Test
    public void testLoginAdmin() {
        var uid = "1100";
        var password = EncryptProvider.getSaltedPassword(uid, "123456");
        var user = new Admin(uid, password);

        teacherMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(null).times(1);
        studentMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(null).times(1);
        adminMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(user).times(1);
        EasyMock.replay(teacherMapper, studentMapper, adminMapper);

        var response = loginService.login("1100", "123456");
        System.out.println(response);
        assertSame(user, response.getData());
        assertEquals(200, response.getCode());
        EasyMock.verify(teacherMapper, studentMapper, adminMapper);
    }

    @Test
    public void testLoginStudent() {
        var uid = "201806061219";
        var password = EncryptProvider.getSaltedPassword(uid, "123456");
        var user = new Student(uid, password);

        teacherMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(null).times(1);
        studentMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(user).times(1);
        EasyMock.replay(teacherMapper, studentMapper);

        var response = loginService.login("201806061219", "123456");
        System.out.println(response);
        assertSame(user, response.getData());
        assertEquals(200, response.getCode());
        EasyMock.verify(teacherMapper, studentMapper);
    }

    @Test
    public void testLoginTeacher() {
        var uid = "1234567";
        var password = EncryptProvider.getSaltedPassword(uid, "123456");
        var user = new Teacher(uid, password);

        teacherMapper.login(uid, password);
        EasyMock.expectLastCall().andReturn(user).times(1);
        EasyMock.replay(teacherMapper);

        var response = loginService.login("1234567", "123456");
        System.out.println(response);
        assertSame(user, response.getData());
        assertEquals(200, response.getCode());
        EasyMock.verify(teacherMapper);
    }

    @Test
    public void testLoginFail() {
        var uid = "1234";
        var password = EncryptProvider.getSaltedPassword(uid, "123456");
        var wrongPassword = EncryptProvider.getSaltedPassword(uid, "1234");

        teacherMapper.login(uid, wrongPassword);
        EasyMock.expectLastCall().andReturn(null).times(1);
        studentMapper.login(uid, wrongPassword);
        EasyMock.expectLastCall().andReturn(null).times(1);
        adminMapper.login(uid, wrongPassword);
        EasyMock.expectLastCall().andReturn(null).times(1);
        EasyMock.replay(teacherMapper, studentMapper, adminMapper);

        var response = loginService.login(uid, "1234");
        System.out.println(response);
        assertSame(null, response.getData());
        assertEquals(403, response.getCode());
        EasyMock.verify(teacherMapper, studentMapper, adminMapper);
    }
}