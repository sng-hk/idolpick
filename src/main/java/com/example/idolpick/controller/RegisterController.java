package com.example.idolpick.controller;

import com.example.idolpick.dto.SignupRequestDto;
import com.example.idolpick.repository.UserRepository;
import com.example.idolpick.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequestDto request) {
        try {
            userRepository.save(request.toEntity());

            // 회원가입 성공 → 토큰 발급
            String token = jwtUtil.generateToken(request.getEmail(), request.getNickname());

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
}
