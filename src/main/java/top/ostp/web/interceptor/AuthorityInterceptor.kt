package top.ostp.web.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorityInterceptor : HandlerInterceptor {
    @Throws(Exception::class)
    override fun preHandle(
        request: HttpServletRequest?,
        response: HttpServletResponse?, handler: Any?,
    ): Boolean {
        println("interceptor/handler: $handler")
        return true
    }

    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        handler: Any?,
        modelAndView: ModelAndView?,
    ) {

    }

    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        handler: Any?,
        exception: Exception?,
    ) {

    }
}