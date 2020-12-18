package top.ostp.web.model.complex

import java.util.*

class ClassAware(var id: Int, var name: String) {
    var students: List<Student> = LinkedList()
}