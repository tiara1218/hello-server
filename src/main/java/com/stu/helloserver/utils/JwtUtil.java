package com.stu.helloserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 生成一个安全的密钥（实际项目中建议配置在配置文件中）
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token 有效期：1小时（毫秒）
    private static final long EXPIRATION_TIME = 3600000;

    /**
     * 生成 Token
     * @param username 用户名
     * @return Token 字符串
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 从 Token 中提取用户名
     * @param token Token 字符串
     * @return 用户名
     */
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * 验证 Token 是否有效
     * @param token Token 字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 提取 Token 中的声明信息
     */
    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}