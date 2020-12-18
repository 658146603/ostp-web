package top.ostp.web.model.common

data class ResponseToken(var code: Int, var summary: String, var defaultMessage: String = "") {
}