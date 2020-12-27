package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses
import top.ostp.web.model.complex.ClassAdvice

@Service
class ClassService {
    @Autowired
    lateinit var clazzMapper: ClazzMapper

    fun selectAllByMajorId(id: Int): ApiResponse<List<ClassAdvice>> {
        return Responses.ok(clazzMapper.selectAllExtendByMajorId(id))
    }
}