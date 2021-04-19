package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.model.Clazz
import top.ostp.web.model.Major
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses
import top.ostp.web.model.complex.ClassAdvance

@Service
class ClassService {
    @Autowired
    lateinit var clazzMapper: ClazzMapper

    fun selectAllByMajorId(id: Int): List<ClassAdvance> {
        return clazzMapper.selectAllExtendByMajorId(id)
    }

    fun selectById(id: Int): Clazz? {
        return clazzMapper.selectById(id.toLong())
    }

    fun selectByMajor(major: Int): List<Clazz> {
        return clazzMapper.selectAllByMajorId(major)
    }

    fun insert(name: String, major: Long): ApiResponse<Any> {
        val clazz = Clazz()
        clazz.name = name
        clazz.major = Major()
        clazz.major?.id = major
        val result = clazzMapper.insert(clazz);
        return if (result == 1) {
            Responses.ok("插入成功")
        } else {
            Responses.fail("失败")
        }
    }

    fun selectByCollegeId(id: Int): ApiResponse<List<Clazz>> {
        return Responses.ok(clazzMapper.selectAllByCollegeId(id));
    }
}