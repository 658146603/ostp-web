package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.CollegeMapper;
import top.ostp.web.mapper.TeacherMapper;
import top.ostp.web.model.College;
import top.ostp.web.model.Teacher;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.util.EncryptProvider;

import java.util.List;

@Service
public class TeacherService {
    TeacherMapper teacherMapper;
    CollegeMapper collegeMapper;

    @Autowired
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Autowired
    public void setCollegeMapper(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
    }

    public ApiResponse<Object> insert(String id, String name, Long college, String password, String email) {
        if (id.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            return Responses.fail("参数存在空值");
        }

        password = EncryptProvider.getSaltedPassword(id, password);
        College c = collegeMapper.selectById(college);
        if (c == null) {
            return Responses.fail("College ["+college+"] Not Found");
        }
        try {
            teacherMapper.insertByVal(id, name, college, password, email);
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
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher != null) {
            return Responses.ok(teacher.erasePassword());
        }
        return Responses.fail("teacher " + id + " not found");
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

    public List<Teacher> selectByCollegeId(int collegeId) {
        return teacherMapper.selectByCollegeId(collegeId);
    }
}
