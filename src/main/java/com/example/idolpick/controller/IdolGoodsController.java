package com.example.idolpick.controller;

import com.example.idolpick.dto.GoodsResponseDto;
import com.example.idolpick.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
