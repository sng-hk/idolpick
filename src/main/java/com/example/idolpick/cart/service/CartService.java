package com.example.idolpick.cart.service;

import com.example.idolpick.cart.repository.CartRepository;
import com.example.idolpick.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void deleteCart(Long user_id, Long product_id) {
    }

    // 장바구니 전체 비우기
    public void truncateCart(Long userId) {
        cartRepository.truncateCart(userId);
    }
}
