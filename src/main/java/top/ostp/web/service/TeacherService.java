package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Student;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

import java.util.List;

@Service
public class TeacherService {
    TeacherMapper teacherMapper;

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public ApiResponse<Object> insert(Teacher teacher) {
        teacher.setPassword(EncryptProvider.getSaltedPassword(teacher.getId(), teacher.getPassword()));
        try {
            teacherMapper.insert(teacher);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<List<Teacher>> selectByName(String name) {
        return Responses.ok(teacherMapper.selectByName(name)); //TODO 抹去密码
    }

    public ApiResponse<Teacher> selectById(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher != null) {
            return Responses.ok(teacher.erasePassword());
        }
        return Responses.fail("teacher " + id + " not found");
    }

    public ApiResponse<Boolean> checkDuplicate(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher != null) {
            return Responses.ok(true);
        } else {
            return Responses.ok(false);
        }
    }

    public ApiResponse<Object> deleteById(Teacher teacher) {
        int result = teacherMapper.delete(teacher);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<List<Teacher>> selectAll() {
        return Responses.ok(teacherMapper.selectAll());//TODO 抹去密码
    }


    public ApiResponse<List<Teacher>> likeByName(String name) {
        return Responses.ok(teacherMapper.likeListByName(name));
    }

    public ApiResponse<Object> updatePassword(Teacher teacher, String password) {
        teacher.setPassword(EncryptProvider.getSaltedPassword(teacher.getId(), teacher.getPassword()));
        password = EncryptProvider.getSaltedPassword(teacher.getId(), password);
        int status = teacherMapper.updatePassword(teacher, password);
        if (status == 1) {
            return Responses.ok("密码修改成功");
        } else {
            return Responses.fail("错误");
        }
    }
}
