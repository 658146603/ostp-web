package top.ostp.web.model.common

data class ApiResponse<T>(var code:Int, var summary:String, var message:String = "", var data: T? = null) {
    constructor(token: ResponseToken, message: String, data: T ?= null) : this(token.code, token.summary, message, data)
    constructor(token: ResponseToken, data: T? = null) : this(token.code, token.summary, token.defaultMessage, data)
}