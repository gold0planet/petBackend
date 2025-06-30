package com.dlnu.pet.config;

import org.springframework.web.filter.OncePerRequestFilter;

import com.dlnu.pet.util.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTConfig extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        String token = extractToken(request);
        if (token != null && JwtUtil.validateToken(token)) {
            Long userId = JwtUtil.parseToken(token);
            // 创建认证对象并存入 SecurityContextHolder
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // 从请求头或请求参数中提取 JWT Token
    private String extractToken(HttpServletRequest request) {
        // 优先从请求头中获取（推荐方式）
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉 "Bearer " 前缀
        }
        // 从 Cookie 中获取（需前端配合将 Token 存入 Cookie）
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null; // 未找到 Token
    }
}