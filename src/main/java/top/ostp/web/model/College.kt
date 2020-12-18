package top.ostp.web.model

import java.io.Serializable

data class College(var id: Int, var name: String) : Serializable {

    constructor(name: String) : this(-1, name)

    constructor() : this(-1, "")

    companion object {
        private const val serialVersionUID = 1L
    }
}