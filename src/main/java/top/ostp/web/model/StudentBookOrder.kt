package top.ostp.web.model

data class StudentBookOrder(
    var id: Long,
    var student: Student?,
    var book: Book?,
    var price: Int,
    var year: Int,
    var semester: Int,
    var received: Int,
) {
    constructor() : this(0, null, null, 0, 0, 0, 0)

    override fun equals(other: Any?): Boolean {
        return super.equals(other) || id == (other as? StudentBookOrder)?.id
    }
}