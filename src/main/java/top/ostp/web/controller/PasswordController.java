package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
    /**
     * 根据密码和学号重置密码并在密码重置成功后向用户发送一封邮件
     *
     * @param id 学生的id
     * @param email 学生的邮箱
     * @return response
     */
    @PostMapping(path = "/password/reset")
    @NoAuthority
    public ApiResponse<Object> resetPassword(String id, String email) {
        return emailService.resetPassword(id, email);
    }
}
