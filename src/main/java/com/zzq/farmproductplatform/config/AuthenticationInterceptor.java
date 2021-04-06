package com.zzq.farmproductplatform.config;

import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.common.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    // 在请求进入处理器之前回调这个方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头
        String token = request.getHeader(AUTHORIZATION);
        // 请求头不为空进行解析
        if (StringUtils.notEmpty(token)) {
            // 按照我们和前端约定的格式进行处理
            // 得到令牌
            System.out.println("=============================");
            System.out.println(token);
            // 验证令牌
            try {
                // 令牌的解析这里一定的try起来,因为它解析错误的令牌时,会报错，当然你也可以在自定义的jwtUtil中把异常 try起来,这里就不用写了
                Claims claims = JWTUtils.parseToken(token);
                System.out.println(claims);
                String roles = (String) claims.get("roles");
                System.err.println("roles==" + roles);
                if ("admin".equals(roles)) {
                    request.setAttribute("role_admin", token);
                    System.out.println("我是admin");
                }
                if ("user".equals(roles)) {
                    request.setAttribute("role_user", token);
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌不存在");
            }
        } else {
            System.out.println("token不存在");
        }

        return true;
    }
}