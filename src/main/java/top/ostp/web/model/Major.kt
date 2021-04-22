package top.ostp.web.model

data class Major(
    var id: Long = 0,
    var name: String,
    var college: College?,
    var year: Int
) {
    constructor() : this(0, "", null, 0)

    override fun equals(other: Any?): Boolean {
        return super.equals(other) || id == (other as? Major)?.id
    }

    constructor(name: String, college: College, year: Int) : this(0, name, college, year)
}