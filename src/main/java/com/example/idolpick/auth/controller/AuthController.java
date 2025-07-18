package com.example.idolpick.auth.controller;

import com.example.idolpick.user.dto.SignupRequestDto;
import com.example.idolpick.user.dto.UserInfoResponseDto;
import com.example.idolpick.user.entity.User;
import com.example.idolpick.user.repository.UserRepository;
import com.example.idolpick.auth.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userinfo")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequestDto request) {
        try {
            userRepository.save(request.toEntity());

            // 회원가입 성공 → 토큰 발급
            String token = jwtUtil.generateToken(request.getEmail(), request.getRole());

            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            // 유효성 오류 등 클라이언트 잘못
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // 서버 내부 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "회원가입 처리 중 오류가 발생했습니다."));
        }
    }

    @GetMapping
    public ResponseEntity<?> user(Authentication authentication) {
        if (authentication != null) {
            Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
            String email = (String) principal.get("email");
            User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
            return ResponseEntity.ok(new UserInfoResponseDto(user));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "회원 정보를 찾을 수 없습니다."));
    }
}
