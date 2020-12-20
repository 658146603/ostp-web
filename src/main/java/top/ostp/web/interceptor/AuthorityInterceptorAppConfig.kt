package top.ostp.web.interceptor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class AuthorityInterceptorAppConfig : WebMvcConfigurer {
    @Autowired
    lateinit var authorityInterceptor: AuthorityInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorityInterceptor)
    }
}