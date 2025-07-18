package com.example.idolpick.order.repository.rowmapper;

import com.example.idolpick.order.entity.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(
                rs.getLong("order_id"),
                rs.getLong("product_id"),
                rs.getInt("quantity"),
                rs.getInt("price_at_order")
        );
    }
}

