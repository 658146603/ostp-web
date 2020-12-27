package top.ostp.web;

import org.junit.jupiter.api.Test;
import top.ostp.web.util.EmailProvider;

public class emailProviderTest {
    @Test
    void resetPassword() {
        assert EmailProvider.email("重置密码成功", "这是你的新密码", "i@huhaorui.com");
    }
}
