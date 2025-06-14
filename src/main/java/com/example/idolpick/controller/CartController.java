package com.example.idolpick.controller;

import com.example.idolpick.dto.CartItemResponseDto;
import com.example.idolpick.dto.CartRequestDto;
import com.example.idolpick.dto.CartResponseDto;
import com.example.idolpick.entity.User;
import com.example.idolpick.repository.CartRepository;
import com.example.idolpick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @PostMapping
    public void addCart(Authentication authentication, @RequestBody CartRequestDto request) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        cartRepository.addCart(user.getId(), request.getProductId(), request.getQuantity());
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        List<CartItemResponseDto> cartItemResponseDto = cartRepository.showCart(user.getId());
        CartResponseDto cartResponseDto = new CartResponseDto(cartItemResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);
    }
}
