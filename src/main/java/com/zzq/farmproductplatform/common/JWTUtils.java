package com.zzq.farmproductplatform.common;

import com.zzq.farmproductplatform.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    public String generateToken(User user) {
        // 生成令牌,主要是用它生成载荷
        JwtBuilder builder = Jwts.builder()
                // 设置头部,使用hs256加密, + key,也就是盐
                .signWith(SignatureAlgorithm.HS256,"zzqzzq")
                // 添加载荷
                .setId("666") // 用户id
                .setSubject(user.getUsername()) // 用户名
                .setExpiration(new Date(new Date().getTime()+60*10000)) // 过期时间
                .setIssuedAt(new Date())// 登录时间
                // 添加自定义的键值对
                .claim("roles",user.getStatus() == 1 ? Const.ADMIN : Const.ORDINARY);
        return builder.compact();
    }
    public Claims parseToken(String token) {

        return Jwts.parser().setSigningKey("zzqzzq").parseClaimsJws(token).getBody();

    }
}
