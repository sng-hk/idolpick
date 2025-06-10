package com.example.idolpick.config;
import com.example.idolpick.repository.UserRepository;
import com.example.idolpick.security.handler.OAuth2AuthenticationSuccessHandler;
import com.example.idolpick.security.jwt.JwtAuthenticationFilter;
import com.example.idolpick.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtUtil jwtUtil, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/api/home", "/api/signup").permitAll()
                        // .hasRole("MD") : 내부적으로 ROLE_MD로 자동으로 변환
                        .requestMatchers("/api/md/**").hasAuthority("ROLE_MD") // 문자열 그대로 비교
                        .requestMatchers("/api/user/**").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                                // OAuth2 로그인 성공시 우리가 만든 핸들러 호출
                                .successHandler(oAuth2AuthenticationSuccessHandler())
                                 // 필요하면 실패 핸들러도 등록 가능
                ).exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                )
                // ✅ JWT 필터 추가!!
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);;
        return http.build();
    }


    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtUtil, userRepository);
    }

    @Bean
    public Filter jwtFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }
}
