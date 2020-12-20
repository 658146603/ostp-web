package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.SessionAttributes
import top.ostp.web.model.Admin
import top.ostp.web.model.Student
import top.ostp.web.model.Teacher
import top.ostp.web.model.annotations.NoAuthority
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses
import top.ostp.web.model.common.Tokens.ok
import top.ostp.web.service.LoginService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@NoAuthority
@Controller
@SessionAttributes(names = ["role"])
class LoginController {
    @Autowired
    lateinit var loginService: LoginService

    @PostMapping(path = ["/login"])
    @ResponseBody
    fun login(id: String?, password: String?, request: HttpServletRequest, res: HttpServletResponse): ApiResponse<Any> {
        val session = request.session
        println("login/session: ${session.id}")
        val response = loginService.login(id, password)
        if (response.code == ok.code && response.data != null) {
            session.setAttribute("username", id)
            when (response.data) {

                is Admin -> session.setAttribute("role", response.data)

                is Teacher -> session.setAttribute("role", response.data)

                is Student -> session.setAttribute("role", response.data)

                else -> TODO()
            }
        }
        return response
    }

    @PostMapping("/account/status")
    @ResponseBody
    fun status(request: HttpServletRequest): ApiResponse<Any> {
        val session = request.session

        val role = session["role"]
        println("status/session: ${session.id}")

        return if (role != null) {
            Responses.ok(role)
        } else {
            Responses.fail()
        }
    }

    @PostMapping("/account/username")
    @ResponseBody
    fun username(request: HttpServletRequest):ApiResponse<Any>{
        val session = request.session
        return Responses.ok(session.getAttribute("role"))
    }

    @PostMapping("/logout")
    @ResponseBody
    fun logout(request: HttpServletRequest): ApiResponse<String> {
        request.session.invalidate()
        return Responses.ok()
    }
}

private operator fun HttpSession.get(attr: String): Any? {
    return this.getAttribute(attr)
}
