package top.ostp.web.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.ostp.web.mapper.BookOrderMapper
import top.ostp.web.mapper.ClazzMapper
import top.ostp.web.mapper.CourseOpenMapper
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses

@Service
class BookOrderService {
    @Autowired
    lateinit var orderMapper: BookOrderMapper

    @Autowired
    lateinit var clazzMapper: ClazzMapper

    @Autowired
    lateinit var openMapper: CourseOpenMapper


    fun getBookOrderListByClazz(clazzId: Long, year: Int, semester: Int) {
        val clazz = clazzMapper.selectById(clazzId)
        val major = clazz?.major ?: return
        val orders = orderMapper.selectByYearSemesterAndClazz(clazz.id, year, semester)
        orders.forEach(::println)
    }

    fun setBookOrderReceivedForClazz(clazzId: Long, year: Int, semester: Int): ApiResponse<Any> {
        val clazz = clazzMapper.selectById(clazzId)

        if (clazz == null) {
            return Responses.fail("班级[$clazz]不存在")
        }

        TODO()
    }
}