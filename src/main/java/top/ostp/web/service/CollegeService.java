package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.CollegeMapper;
import top.ostp.web.model.College;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;
import top.ostp.web.model.complex.CollegeAdvance;

import java.util.List;

@Service
public class CollegeService {
    CollegeMapper collegeMapper;

    @Autowired
    public void setCollegeMapper(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
    }

    public ApiResponse<Object> insert(College college) {
        try {
            int result = collegeMapper.insert(college);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<College> insertAndGetId(String name) {
        collegeMapper.insertByName(name);
        College college = collegeMapper.selectByName(name);
        if (college != null)
            return Responses.ok(college);
        else {
            return Responses.fail("创建失败");
        }
    }

    public ApiResponse<College> selectByName(String name) {
        return Responses.ok(collegeMapper.selectByName(name));
    }

    public ApiResponse<Object> deleteById(int id) {
        int result = collegeMapper.deleteById(id);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("删除失败");
        }
    }

    public ApiResponse<Object> insertByName(String name) {
        int result = collegeMapper.insertByName(name);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("主键重复");
        }
    }

    public ApiResponse<College> selectById(int id) {
        return Responses.ok(collegeMapper.selectById(id));
    }

    public ApiResponse<List<CollegeAdvance>> selectAll() {
        return Responses.ok(collegeMapper.selectAllExtend());
    }

    public ApiResponse<List<College>> fuzzy(String name) {
        return Responses.ok(collegeMapper.likeByName(name));
    }

    public ApiResponse<College> selectByMajorId(int id) {
        College result = collegeMapper.selectByMajorId(id);
        if (result == null) {
            return Responses.fail();
        } else {
            return Responses.ok(result);
        }
    }
}
