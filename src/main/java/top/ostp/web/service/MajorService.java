package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.MajorMapper;
import top.ostp.web.model.Major;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

import java.util.List;

@Service
public class MajorService {

    MajorMapper majorMapper;

    @Autowired
    public void setMajorMapper(MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
    }

    public ApiResponse<Object> insert(Major major) {
        try {
            majorMapper.insert(major);
            return Responses.ok("插入成功");
        } catch (DuplicateKeyException e) {
            return Responses.fail("插入出错");
        }
    }

    public ApiResponse<Object> selectAll() {
        List<Major> majors = majorMapper.selectAll();
        if (majors.isEmpty()) {
            return Responses.fail("没有专业");
        } else {
            return Responses.ok(majors);
        }
    }

    public ApiResponse<Object> update(Major major) {
        int result = majorMapper.update(major);
        if (result == 1) {
            return Responses.ok("更新成功");
        } else {
            return Responses.fail("更新失败");
        }
    }

    public ApiResponse<Object> delete(Major major) {
        int result = majorMapper.delete(major);
        if (result == 1) {
            return Responses.ok("删除成功");
        } else {
            return Responses.fail("删除失败");
        }
    }
}
