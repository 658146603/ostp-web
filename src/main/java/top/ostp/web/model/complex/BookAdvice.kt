package top.ostp.web.model.complex

import top.ostp.web.model.Book
import java.io.Serializable
import java.util.*

/**
 * 增强的book，能标识比book更多的逻辑
 */
data class BookAdvice(var book: Book): Serializable {
    val serialVersionUID = 1L
    var ordered: Boolean = false
    var courses: List<String> = LinkedList()
}