package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;

@Service
public class TeacherService {
    TeacherMapper teacherMapper;

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public ApiResponse<Object> insert(Teacher teacher) {
        try {
            teacherMapper.insert(teacher);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<List<Teacher>> selectByName(String name) {
        return Responses.ok(teacherMapper.selectByName(name));
    }

    public ApiResponse<Teacher> selectById(String id) {
        return Responses.ok(teacherMapper.selectById(id));
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
        return Responses.ok(teacherMapper.selectAll());
    }

}
