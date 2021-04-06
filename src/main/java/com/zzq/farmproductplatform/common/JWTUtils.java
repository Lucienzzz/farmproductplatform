package com.zzq.farmproductplatform.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;

public class JWTUtils {

    public static final String SALT = "zzqzzq";

    /**
     * token 过期时间，60 分钟
     */
    public static final int TOKEN_EXPIRE_MINUTES = 60;

    public static String generateToken(String username) {
        // 生成令牌,主要是用它生成载荷
        OffsetDateTime now = OffsetDateTime.now();
        Date date = new Date(now.toEpochSecond());
        JwtBuilder builder = Jwts.builder()
                // 设置头部,使用hs256加密, + key,也就是盐
                .signWith(SignatureAlgorithm.HS256, "zzqzzq")
                // 用户id
                .setId("666")
                // 用户名
                .setSubject(username)
                // 过期时间
                .setExpiration(new Date(now.plusMinutes(TOKEN_EXPIRE_MINUTES).toEpochSecond()))
                // 登录时间
                .setIssuedAt(date)
                // 添加自定义的键值对
                .claim("roles", "admin");
        return builder.compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
    }
}
