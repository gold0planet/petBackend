package com.dlnu.pet.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // 设置token过期时间为7天
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
    
    // 使用安全的密钥生成方法
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    /**
     * 生成Token
     * @param userId 用户ID
     * @return Token字符串
     */
    public static String generateToken(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRE_TIME);
        
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
    }
    
    /**
     * 解析Token
     * @param token Token字符串
     * @return 用户ID
     */
    public static Long parseToken(String token) {
        return Long.parseLong(
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
        );
    }
    /**
     * 验证Token
     * @param token Token字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 