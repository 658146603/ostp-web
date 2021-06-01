package top.ostp.web.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidProvider {
    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
