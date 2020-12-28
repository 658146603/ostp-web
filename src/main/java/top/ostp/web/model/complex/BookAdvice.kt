package top.ostp.web.model.complex

import top.ostp.web.model.Book
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.StudentBookOrder
import java.io.Serializable
import java.util.*

/**
 * 增强的book，能标识比book更多的数据
 */
data class BookAdvice(var book: Book, var courseOpens: List<CourseOpen>, var orders: List<StudentBookOrder>, var ordered: Boolean = false): Serializable {
    val serialVersionUID = 1L
    var courses: List<String> = courseOpens.map { it.course.name }.distinct().toList()
}