package com.zzq.farmproductplatform.config;

import com.zzq.farmproductplatform.common.AuthToken;
import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
class MyInterceptor implements HandlerInterceptor {

    JWTUtils jwtUtils;
    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    private RedisTemplate<String, String> template;

    @Autowired
    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    // 在请求进入处理器之前回调这个方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String httpHeader = "Authorization";
            String token = request.getParameter(httpHeader);
            if (!StringUtils.isEmpty(token)) {
                String username = template.opsForValue().get(token);
                if (!StringUtils.isEmpty(username)) {
                    request.setAttribute("curName", username);
                    return true;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return false;
            }
        }
        request.setAttribute("curName", null);
        return true;
    }
}
