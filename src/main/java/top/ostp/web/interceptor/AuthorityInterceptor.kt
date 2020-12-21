package top.ostp.web.interceptor

import com.google.gson.Gson
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import top.ostp.web.model.Admin
import top.ostp.web.model.Student
import top.ostp.web.model.Teacher
import top.ostp.web.model.annotations.*
import top.ostp.web.model.common.Responses
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

        if (handler is HandlerMethod && handler.beanType.getAnnotation(NoAuthority::class.java) == null) {
            handler.beanType.getAnnotation(Blame::class.java)?.let { println(it.value) }

            val role = request?.session?.getAttribute("role")
            when {
                handler.method.isAnnotationPresent(AuthStudent::class.java) -> {
                    if (Student::class.java == role?.javaClass) {
                        println("auth success - student")
                        return true
                    }
                }

                handler.method.isAnnotationPresent(AuthTeacher::class.java) -> {
                    if (Teacher::class.java == role?.javaClass) {
                        println("auth success - teacher")
                        return true
                    }
                }

                handler.method.isAnnotationPresent(AuthAdmin::class.java) -> {
                    if (Admin::class.java == role?.javaClass) {
                        println("auth success - admin")
                        return true
                    }
                }

                handler.method.isAnnotationPresent(NoAuthority::class.java) -> {
                    return true
                }

                else -> {
                    TODO("既没有NoAuthority也没有权限控制")
                }
            }

            println("auth failed, role is $role")
            response?.contentType = "text/html;charset=UTF-8"
            response?.status = HttpServletResponse.SC_UNAUTHORIZED
            response?.writer?.println(Gson().toJson(Responses.fail<Any>("权限校验失败")))
            return false
        } else if (handler is HandlerMethod) {
            handler.beanType.getAnnotation(Blame::class.java)?.let { println(it.value) }
            println("no auth needed")
        } else {
            println("非HandlerMethod调用，放行")
        }

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