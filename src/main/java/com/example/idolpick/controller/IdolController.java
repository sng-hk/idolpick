package com.example.idolpick.controller;

import com.example.idolpick.entity.Idol;
import com.example.idolpick.entity.User;
import com.example.idolpick.repository.IdolRepository;
import com.example.idolpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/idol")
public class IdolController {

    private final IdolRepository idolRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Idol>> getIdols(@RequestParam(required = false) String name) {
        // ✅ 전체 아이돌 리스트 조회
        if (name != null && !name.isBlank()) { // 아이돌 이름 검색
            return ResponseEntity.ok(idolRepository.findByNameLike(name));
        }
        return ResponseEntity.ok(idolRepository.findAll());
    }

    @PostMapping("/like/{id}")
    public void likeIdol(@PathVariable Long id, Authentication authentication) { // 아이돌 찾기
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Idol idol = idolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("아이돌 정보를 찾을 수 없습니다.")); // 아이돌 정보 찾기
        idolRepository.likeIdol(user.getId(), idol.getId());
    }

    @DeleteMapping("/like/{id}")
    public void unlikeIdol(@PathVariable Long id, Authentication authentication) { // 아이돌 찾기
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Idol idol = idolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("아이돌 정보를 찾을 수 없습니다.")); // 아이돌 정보 찾기
        idolRepository.unlikeIdol(user.getId(), idol.getId());
    }
}