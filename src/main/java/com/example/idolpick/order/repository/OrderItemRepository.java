package com.example.idolpick.order.repository;

import com.example.idolpick.order.entity.OrderItem;
import com.example.idolpick.order.repository.rowmapper.OrderItemRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId);
    }
}

