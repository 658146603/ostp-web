package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
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
class LoginController {
    @Autowired
    lateinit var loginService: LoginService

    /**
     * 登录。在service层使用账号密码分别以学生，教师管理员的身份登录。登录成功则返回
     * 不成功，以其他身份登录
     *
     * @param id 登录的账号
     * @param password 登录的密码
     * @param request servletRequest。又来设置session中的role
     * @return response
     */
    @PostMapping(path = ["/login"])
    @ResponseBody
    fun login(id: String?, password: String?, request: HttpServletRequest, res: HttpServletResponse): ApiResponse<Any> {
        val session = request.session
        val response = loginService.login(id, password)
        if (response.code == ok.code && response.data != null) {
            session.setAttribute("username", id)
            session["role"] = response.data
        }
        return response
    }

    /**
     * 从session中获取当前登录的身份，若获取不到则返回未登录
     *
     * @param request servletRequest。又来获取session中的role
     * @return response
     */
    @PostMapping("/account/status")
    @ResponseBody
    fun status(request: HttpServletRequest): ApiResponse<Any> {
        val session = request.session
        var role = session["role"]
        return if (role != null) {
            role = loginService.findById(role)
            session["role"] = role
            Responses.ok(role)
        } else {
            Responses.fail("未登录")
        }
    }

    @PostMapping("/account/username")
    @ResponseBody
    fun username(request: HttpServletRequest): ApiResponse<Any> {
        var role = request.session["role"]
        return if (role != null) {
            role = loginService.findById(role)
            request.session["role"] = role
            Responses.ok(role)
        } else {
            Responses.fail("未登录")
        }
    }
    /**
     * 注销该用户。将其状态和用户名都设置成空
     *
     * @param request servletRequest。又来获取session中的role
     * @return response
     */

    @PostMapping("/logout")
    @ResponseBody
    fun logout(request: HttpServletRequest): ApiResponse<String> {
        request.session.setAttribute("role", null)
        request.session.setAttribute("username", null)
        return Responses.ok("logout success: ${request.session.getAttribute("role")}")
    }

    /**
     * 在新建一个学生，老师，管理员的时候对其id字段进行校验
     *
     * @param id 待校验的id
     * @return response
     */
    @PostMapping(value = ["/account/duplicate"])
    @ResponseBody
    fun duplicate(id: String): ApiResponse<Boolean> {
        return loginService.checkDuplicate(id)
    }

    private operator fun HttpSession.get(attr: String): Any? {
        return this.getAttribute(attr)
    }

    private operator fun HttpSession.set(attr: String, value: Any?) {
        this.setAttribute(attr, value)
    }
}

