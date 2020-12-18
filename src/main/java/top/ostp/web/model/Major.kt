package top.ostp.web.model

data class Major(
    var id: Long = 0,
    var name: String,
    var college: College?
) {
    constructor() : this(0, "", null)

    constructor(name: String, college: College) : this(0, name, college)
}