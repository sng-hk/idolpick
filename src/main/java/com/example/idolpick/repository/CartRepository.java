package com.example.idolpick.repository;

import com.example.idolpick.dto.CartItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    public CartItemResponseDto showCart(Long user_id) {
        String sql = """
                SELECT
                    c.product_id,
                    p.name,
                    p.price,
                    c.quantity,
                    p.stock,
                    (p.price * c.quantity) as total
                FROM cart c
                JOIN product p ON c.product_id = p.id
                WHERE c.user_id = 14;
                """;

        List<CartItemResponseDto> result = jdbcTemplate.query(sql, (rs, rowNum) -> new CartItemResponseDto(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("quantity"),
                rs.getInt("total")
        ));
    }

    public void deleteCart(Long user_id, Long product_id) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, user_id, product_id);
    }

    public void updateCart(Long user_id, Long product_id, Integer quantity) {

    }

    public void addCart(Long user_id, Long product_id, Integer quantity) {
        // 장바구니에 동일 상품이 없으면 → 새로 INSERT
        //
        // 장바구니에 이미 존재하면 → 수량만 누적, updated_at 갱신
        String sql = """
                INSERT INTO cart (user_id, product_id, quantity)
                VALUES (?, ?, ?)              
                ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity) 
                """;

        jdbcTemplate.update(sql, user_id, product_id, quantity);
    }
}
