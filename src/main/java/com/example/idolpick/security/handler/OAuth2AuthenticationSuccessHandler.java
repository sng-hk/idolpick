package com.example.idolpick.security.handler;

import com.example.idolpick.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil; // JWT 생성 클래스

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // JWT 토큰 생성 (너가 만든 jwtProvider에서 맞게 구현했어야 함)
        log.info("email: " + oAuth2User.getAttribute("email"));
        log.info("nickname: " + oAuth2User.getAttribute("nickname"));
        String token = jwtUtil.generateToken(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("nickname"));

        // 프론트엔드 URL로 JWT를 쿼리 파라미터로 넘겨서 리다이렉트
        String redirectUrl = "http://localhost:3000/oauth/callback?token=" + token;

        response.sendRedirect(redirectUrl);
    }
}
