package top.ostp.web.model

data class Class(
    var id: Long,
    var name: String,
    var major: Major?
) {
    constructor() : this(-1, "", null)

    constructor(name: String, major: Major) : this(-1, name, major)
}