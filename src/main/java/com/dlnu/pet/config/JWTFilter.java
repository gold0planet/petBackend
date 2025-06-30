package com.dlnu.pet.config;

import org.springframework.web.filter.OncePerRequestFilter;

import com.dlnu.pet.util.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.dlnu.pet.pojo.entity.LoginUser;

public class JWTFilter extends OncePerRequestFilter {
    
    // 从请求头或请求参数中提取 JWT Token
    private String extractToken(HttpServletRequest request) {
        // 优先从请求头中获取（推荐方式）
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉 "Bearer " 前缀
        }
        return null; // 未找到 Token
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        String token = extractToken(request);
        if (token != null && JwtUtil.validateToken(token)) {
            // 解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Long userId = (Long) claims.get("userId");
            String phone = (String) claims.get("phone");
            // 构造认证对象
            UserDetails userDetails = new LoginUser(userId, phone);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}