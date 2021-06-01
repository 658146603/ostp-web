package top.ostp.web.model.common

object Responses {
    @JvmStatic
    fun <T> validateError() = ApiResponse<T>(Tokens.validateError)

    @JvmStatic
    fun <T> validateError(message: String) = ApiResponse<T>(Tokens.validateError, message)

    @JvmStatic
    fun <T> validateError(message: String, data: T) = ApiResponse(Tokens.validateError, message, data)

    @JvmStatic
    fun <T> validateError(data: T) = ApiResponse(Tokens.validateError, data)

    @JvmStatic
    fun <T> ok() = ApiResponse<T>(Tokens.ok)

    @JvmStatic
    fun <T> ok(message: String) = ApiResponse<T>(Tokens.ok, message)

    @JvmStatic
    fun <T> ok(message: String, data: T) = ApiResponse(Tokens.ok, message, data)

    @JvmStatic
    fun <T> ok(data: T) = ApiResponse(Tokens.ok, data)

    @JvmStatic
    fun <T> fail() = ApiResponse<T>(Tokens.failed)

    @JvmStatic
    fun <T> fail(message: String) = ApiResponse<T>(Tokens.failed, message)

    @JvmStatic
    fun <T> fail(message: String, data: T) = ApiResponse(Tokens.failed, message, data)

    @JvmStatic
    fun <T> fail(data: T) = ApiResponse(Tokens.failed, data)

    /**
     * return a response with token, which is stored in @Tokens
     * @see ResponseToken
     */
    @JvmStatic
    fun <T> withToken(token: ResponseToken) = ApiResponse<T>(token)

    /**
     * return a response with token and message, which is stored in @Tokens
     * @see ResponseToken
     */
    @JvmStatic
    fun <T> withToken(token: ResponseToken, message: String) = ApiResponse<T>(token, message)

    /**
     * return a response with token, message and data, which is stored in @Tokens
     * @see ResponseToken
     */
    @JvmStatic
    fun <T> withToken(token: ResponseToken, message: String, data: T) = ApiResponse<T>(token, message, data)

    /**
     * return a response with token and data, which is stored in @Tokens
     * @see ResponseToken
     */
    @JvmStatic
    fun <T> withToken(token: ResponseToken, data: T) = ApiResponse(token, data)

    /**
     * return a response with code and summary \\
     * [be careful to use this function, please replace it with withToken(...)
     * and then add a token in @Tokens]
     */
    @JvmStatic
    fun <T> code(code: Int, summary: String) = ApiResponse<T>(code, summary)

    /**
     * return a response with code, summary and message \\
     * [be careful to use this function, please replace it with withToken(...)
     * and then add a token in @Tokens]
     */
    @JvmStatic
    fun <T> code(code: Int, summary: String, message: String) = ApiResponse<T>(code, summary, message)

    /**
     * return a response with code, summary, message, data \\
     * [be careful to use this function, please replace it with withToken(...)
     * and then add a token in @Tokens]
     */
    @JvmStatic
    fun <T> code(code: Int, summary: String, message: String, data: T) = ApiResponse<T>(code, summary, message, data)

    /**
     * return a response with code, summary, message="", data \\
     * [be careful to use this function, please replace it with withToken(...)
     * and then add a token in @Tokens]
     */
    @JvmStatic
    fun <T> code(code: Int, summary: String, data: T) = ApiResponse<T>(code, summary, "", data)
}