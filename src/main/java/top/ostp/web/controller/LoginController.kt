package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.LoginService;

@Controller
public class LoginController {
    LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public ApiResponse<Object> login(String id, String password) {
        return loginService.login(id, password);
    }
}
