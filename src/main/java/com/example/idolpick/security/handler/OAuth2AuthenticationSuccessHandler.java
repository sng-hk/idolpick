package com.example.idolpick.security.handler;

import com.example.idolpick.entity.User;
import com.example.idolpick.repository.UserRepository;
import com.example.idolpick.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil; // JWT 생성 클래스
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String nickname = oAuth2User.getAttribute("nickname");

        Optional<User> userOptional = userRepository.findByEmail(email);

        String redirectURL;

        if (userOptional.isPresent()) {
            // 기존 회원이면 JWT 생성
            String token = jwtUtil.generateToken(email, nickname);
            // 토큰을 쿼리 파라미터로 붙여서 프론트 메인 페이지로 리다이렉트
            redirectURL = "http://localhost:3000/oauth/callback?token=" + token + "&isNew=false";
        } else {
            // 신규 회원이면 email, nickname 쿼리스트링으로 회원가입 페이지로 리다이렉트
            String nicknameEncoded = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
            String emailEncoded = URLEncoder.encode(email, StandardCharsets.UTF_8);
            redirectURL = "http://localhost:3000/oauth/callback?email=" + emailEncoded + "&nickname=" + nicknameEncoded + "&isNew=true";
        }
        response.sendRedirect(redirectURL);
    }
}
