package top.ostp.web.model

data class Teacher(
    var id: String,
    var name: String,
    var college: College?,
    var password: String,
    var email: String
) {
    fun erasePassword(): Teacher {
        return Teacher(id, name, college, "", email)
    }

    constructor() : this("", "", null, "", "")
    constructor(uid: String, password: String) : this(uid, "", null, password, "")
}