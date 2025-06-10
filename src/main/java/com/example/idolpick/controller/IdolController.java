package com.example.idolpick.controller;

import com.example.idolpick.entity.Idol;
import com.example.idolpick.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/idol")
public class IdolController {

    private final IdolRepository idolRepository;

    @GetMapping
    public ResponseEntity<List<Idol>> getIdols(@RequestParam(required = false) String name) {
        // ✅ 전체 아이돌 리스트 조회
        if (name != null && !name.isBlank()) { // 아이돌 이름 검색
            return ResponseEntity.ok(idolRepository.findByNameLike(name));
        }
        return ResponseEntity.ok(idolRepository.findAll());
    }
}