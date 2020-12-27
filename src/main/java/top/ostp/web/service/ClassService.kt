package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.model.Clazz
import top.ostp.web.model.complex.ClassAdvice

@Service
class ClassService {
    @Autowired
    lateinit var clazzMapper: ClazzMapper

    fun selectAllByMajorId(id: Int): List<ClassAdvice> {
        return clazzMapper.selectAllExtendByMajorId(id)
    }

    fun selectById(id: Int): Clazz? {
        return clazzMapper.selectById(id.toLong())
    }
}