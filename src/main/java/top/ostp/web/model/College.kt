package top.ostp.web.model

import java.io.Serializable

data class College(var id: Int, var name: String) : Serializable {

    constructor(name: String) : this(-1, name)

    constructor() : this(-1, "")

    override fun equals(other: Any?): Boolean {
        return super.equals(other) || id == (other as? College)?.id
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}