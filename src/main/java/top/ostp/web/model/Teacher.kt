package top.ostp.web.model

data class Teacher(
    var id: String,
    var name: String,
    var college: College?,
    var password: String,
    var email: String
) {
    constructor() : this("", "", null, "", "")
}