package com.dlnu.pet.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http
        //     .csrf().disable()
        //     .authorizeHttpRequests(auth -> auth
        //         .requestMatchers("/api/auth/**").permitAll()  // 公开接口
        //         .anyRequest().authenticated()             // 其他需要认证
        //     )
        //     .sessionManagement()
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 无状态会话
        //         .and()
        //     .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // http.securityMatcher("/api/auth/**");
        http.logout(logout -> logout
            .logoutUrl("/logout") // 默认就是 /logout
            .logoutSuccessUrl("/login") // 登出后跳转
            .invalidateHttpSession(true) // 使session失效
            .clearAuthentication(true) // 清除认证信息
            // .deleteCookies("JSESSIONID") // 如有需要可清除cookie
        );
        return http.build();
    }
}
