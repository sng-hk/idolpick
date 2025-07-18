package com.example.idolpick.order.repository;

import com.example.idolpick.order.dto.OrderDto;
import com.example.idolpick.order.entity.Order;
import com.example.idolpick.order.entity.OrderItem;
import com.example.idolpick.order.repository.rowmapper.OrderRowMapper;
import com.example.idolpick.payment.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public void updateTotalAmount(Long orderId, int totalAmount) {
        String sql = "UPDATE orders SET total_amount = ? WHERE id = ?";
        jdbcTemplate.update(sql, totalAmount, orderId);
    }

    public Order updateOrderStatus(String merchantUid, String status) {
        String sql = "UPDATE orders SET status = ? WHERE merchant_uid = ?";
        jdbcTemplate.update(sql, status, merchantUid);

        String selectSql = "SELECT * FROM orders WHERE merchant_uid = ?";
        return jdbcTemplate.queryForObject(selectSql, new OrderRowMapper(), merchantUid);
    }

    public Long createOrder(Long userId, String merchantUid, int totalAmount) {
        String sql = "INSERT INTO orders (user_id, merchant_uid, total_amount, status) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setString(2, merchantUid);
            ps.setInt(3, totalAmount);
            ps.setString(4, "UNPAID");
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();  // 주문 ID 반환
    }

    public void insertOrderItems(List<OrderItem> items) {
        String sql = "INSERT INTO order_item " +
                "(order_id, product_id, quantity, price_at_order) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderItem item = items.get(i);
                ps.setLong(1, item.getOrderId());
                ps.setLong(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setInt(4, item.getPrice());
            }

            public int getBatchSize() {
                return items.size();
            }
        });
    }

    public int getProductPrice(Long productId) {
        String sql = "SELECT price FROM product WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{productId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public List<OrderDto.OrderReadResponseDto> findOrdersByUser(Long userId) {
        String sql = """
        SELECT o.id as order_id, o.merchant_uid, o.status, o.total_amount, o.created_at as order_created_at,
               oi.quantity, oi.price_at_order,
               p.id as product_id, p.name as product_name, p.thumbnail_url
        FROM orders o
        JOIN order_item oi ON o.id = oi.order_id
        JOIN product p ON p.id = oi.product_id
        WHERE o.user_id = ?
        ORDER BY o.created_at DESC
        """;

        return jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
            Map<Long, OrderDto.OrderReadResponseDto> orderMap = new LinkedHashMap<>();

            while (rs.next()) {
                Long orderId = rs.getLong("order_id");
                OrderDto.OrderReadResponseDto order = orderMap.computeIfAbsent(orderId, id -> {
                    try {
                        OrderDto.OrderReadResponseDto dto = new OrderDto.OrderReadResponseDto();
                        dto.setOrderId(id);
                        dto.setMerchantUid(rs.getString("merchant_uid"));
                        dto.setStatus(PaymentStatus.valueOf(rs.getString("status")));
                        dto.setTotalAmount(rs.getInt("total_amount"));
                        dto.setCreatedAt(rs.getTimestamp("order_created_at").toLocalDateTime());
                        dto.setItems(new ArrayList<>());
                        return dto;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                OrderDto.OrderItemReadResponseDto item = new OrderDto.OrderItemReadResponseDto();
                item.setProductId(rs.getLong("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPriceAtOrder(rs.getInt("price_at_order"));
                order.getItems().add(item);
            }

            return new ArrayList<>(orderMap.values());
        });
    }

}
