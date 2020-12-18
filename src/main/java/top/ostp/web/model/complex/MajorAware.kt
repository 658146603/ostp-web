package top.ostp.web.model.complex

import java.util.*

data class MajorAware(var id: Int, var name: String) {
    var classes: List<ClassAware> = LinkedList()
}