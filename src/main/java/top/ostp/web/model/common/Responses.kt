package top.ostp.web.model.common

object Responses {
    fun <T> validateError(message: String) = ApiResponse<T>(Tokens.validateError, message)
    fun <T> ok(message: String, data: T? = null) = ApiResponse(Tokens.ok, message, data)
    fun <T> withToken(token: ResponseToken, data: T? = null) = ApiResponse(token, data)
}