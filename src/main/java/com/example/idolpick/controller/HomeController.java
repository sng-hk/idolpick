// src/main/java/com/example/idolpick/controller/HomeController.java
package com.example.idolpick.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping
    public Map<String, Object> home(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("인증된 사용자가 아닙니다.");
        }
        Map<String, String> userInfo = (Map<String, String>) authentication.getPrincipal();

        String nickname = userInfo.get("nickname");

        return Map.of(
                "message", "🎉 인증된 사용자입니다!",
                "nickname", nickname
        );
    }
}
