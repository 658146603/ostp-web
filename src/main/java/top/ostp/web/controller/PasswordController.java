package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.EmailService;

@RestController
public class PasswordController {
    EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(path = "/password/reset")
    @NoAuthority
    public ApiResponse<Object> resetPassword(String id, String email) {
        return emailService.resetPassword(id, email);
    }
}
