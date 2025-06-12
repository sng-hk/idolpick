package com.example.idolpick.controller;

import com.example.idolpick.dto.GoodsResponseDto;
import com.example.idolpick.entity.User;
import com.example.idolpick.repository.ProductRepository;
import com.example.idolpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/idol/goods")
public class IdolGoodsController {
    private final ProductRepository productRepository;

    @GetMapping("/{idolId}")
    public ResponseEntity<?> idolGoods(@PathVariable Long idolId) {
        List<GoodsResponseDto> products = productRepository.findAllByIdolId(idolId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/spec/{id}")
    public ResponseEntity<?> idolGoodsSpec(@PathVariable Long id) {
        GoodsResponseDto goodsResponseDto = productRepository.findGoodsById(id).orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));
        return ResponseEntity.status(HttpStatus.OK).body(goodsResponseDto);
    }
}
