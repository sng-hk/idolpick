package com.example.idolpick.order.repository.rowmapper;

import com.example.idolpick.order.entity.Order;
import com.example.idolpick.order.entity.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .totalPrice(rs.getInt("total_amount"))
                .merchantUid(rs.getString("merchant_uid"))
                .address(rs.getString("address"))
                .receiverName(rs.getString("receiver_name"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
