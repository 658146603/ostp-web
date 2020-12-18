package top.ostp.web.model.complex

import java.util.*

data class CollegeAware(var id: Int, var name: String) {
    var teachers: List<Teacher> = LinkedList()
    var majors: List<MajorAware> = LinkedList()
}