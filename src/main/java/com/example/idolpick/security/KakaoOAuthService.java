package com.example.idolpick.security;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoOAuthService {
    private final WebClient webClient = WebClient.create();

    private final String clientId = "YOUR_KAKAO_REST_API_KEY";
    private final String redirectUri = "http://localhost:3000/oauth/callback/kakao";

    public Mono<KakaoUser> getUserInfo(String code) {
        return getAccessToken(code)
                .flatMap(this::getUserInfoByAccessToken);
    }

    private Mono<String> getAccessToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue("grant_type=authorization_code" +
                        "&client_id=" + clientId +
                        "&redirect_uri=" + redirectUri +
                        "&code=" + code)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .map(KakaoTokenResponse::getAccess_token);
    }

    private Mono<KakaoUser> getUserInfoByAccessToken(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUser.class);
    }

    // DTO들
    public static class KakaoTokenResponse {
        private String access_token;
        public String getAccess_token() { return access_token; }
        public void setAccess_token(String access_token) { this.access_token = access_token; }
    }

    public static class KakaoUser {
        private Long id;
        private KakaoAccount kakao_account;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public KakaoAccount getKakao_account() { return kakao_account; }
        public void setKakao_account(KakaoAccount kakao_account) { this.kakao_account = kakao_account; }

        public static class KakaoAccount {
            private Profile profile;
            private String email;

            public Profile getProfile() { return profile; }
            public void setProfile(Profile profile) { this.profile = profile; }
            public String getEmail() { return email; }
            public void setEmail(String email) { this.email = email; }

            public static class Profile {
                private String nickname;

                public String getNickname() { return nickname; }
                public void setNickname(String nickname) { this.nickname = nickname; }
            }
        }
    }
}

