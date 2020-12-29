package top.ostp.web.model.complex

import top.ostp.web.model.Book
import top.ostp.web.model.CourseOpen
import top.ostp.web.model.StudentBookOrder
import java.io.Serializable
import java.util.*

/**
 * 增强的book，能标识比book更多的数据
 */
data class BookAdvice(var book: Book, var courseOpens: List<CourseOpen>, var orders: List<StudentBookOrder>) :
    Serializable {
    val serialVersionUID = 1L
    var orderState: Int = 0
    var courses: List<String> = courseOpens.map { it.course.name }.distinct().toList()
    fun calculateStudent(): BookAdvice {
        orderState = if (!orders.any()) {
            0
        } else if (orders[0].received == 1) {
            2
        } else {
            1
        }
        return this;
    }

    fun calculateTeacher(params2: SearchParams2): BookAdvice {
        orderState = if (courseOpens.find {
                it.received == 1 && it.year == params2.year && it.semester == params2.semester
            } == null) {
            0
        } else {
            1
        }
        return this;
    }

    /**
     * 从教师出发选取一项该学年，学期，且没有领取书籍的开课
     */
    fun selectTeacher(params2: SearchParams2): CourseOpen? {
        return courseOpens.find { it.received == 0 && it.year == params2.year && it.semester == params2.semester }
    }
}