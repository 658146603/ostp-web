package top.ostp.web.model.common

object Tokens {
    val validateError = ResponseToken(400, "validate_error", "validate_error")
    val ok = ResponseToken(200, "ok", "ok")
    object User {
        //TODO: add tokens with user related action.
    }
}