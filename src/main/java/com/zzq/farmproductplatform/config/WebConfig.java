package com.zzq.farmproductplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    static final String[] ALLOW_METHODS = new String[]{"GET", "POST", "PUT", "DELETE"};

    @Autowired
    AuthenticationInterceptor authenticationInterceptor;

    /**
     * 注册拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login/**");
    }

    /**
     * 之前设置的是 allowedOrigin("*") ，会报错：
     * When allowCredentials is true, allowedOrigins cannot contain the special value "*"
     * since that cannot be set on the "Access-Control-Allow-Origin" response header.
     * To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
     * <p>
     * 当设置allowCredentials为true，allowedOrigins的值不能为特殊符号 *，因为在响应头里不能设置"Access-Control-Allow-Origin" 的值为*
     * 然后提示我们应该用 allowedOriginPatterns 方法来代替
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowCredentials(true)
            .allowedMethods(ALLOW_METHODS)
            .maxAge(3600);
    }
}