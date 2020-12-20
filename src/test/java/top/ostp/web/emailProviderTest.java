package top.ostp.web;

import org.junit.jupiter.api.Test;
import top.ostp.web.util.EmailProvider;

public class emailProviderTest {
    @Test
    void resetPassword() {
        assert EmailProvider.resetPassword("https://huhaorui.com", "i@huhaorui.com");
    }
}
