package top.ostp.web.model

data class Course(
    var id: String,
    var major: Major?,
    var name: String
) {
    constructor(): this("", null, "")
}