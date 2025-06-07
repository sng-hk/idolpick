package com.example.idolpick.controller;

import com.example.idolpick.security.KakaoOAuthService;
import com.example.idolpick.security.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/kakao")
public class KakaoOAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    public KakaoOAuthController(KakaoOAuthService kakaoOAuthService) {
        this.kakaoOAuthService = kakaoOAuthService;
    }

    @PostMapping("/callback")
    public Mono<ResponseEntity<?>> kakaoLogin(@RequestBody CodeRequest codeRequest) {
        return kakaoOAuthService.getUserInfo(codeRequest.getCode())
                .map(user -> {
                    // JWT 발급
                    String token = JwtUtil.generateToken(
                            String.valueOf(user.getId()),
                            user.getKakao_account().getProfile().getNickname()
                    );

                    // 클라이언트에 토큰 + 사용자 정보 내려줌
                    return ResponseEntity.ok(new LoginResponse(token, user.getKakao_account().getProfile().getNickname()));
                });
    }

    public static class CodeRequest {
        private String code;
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }

    public static class LoginResponse {
        private String token;
        private String nickname;

        public LoginResponse(String token, String nickname) {
            this.token = token;
            this.nickname = nickname;
        }
        public String getToken() { return token; }
        public String getNickname() { return nickname; }
    }
}
