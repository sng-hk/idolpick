package com.example.idolpick.repository.rowmapper;

import com.example.idolpick.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getLong("id"))
                .idolId(rs.getLong("idol_id"))
                .name(rs.getString("name"))
                .categoryId(rs.getLong("category_id"))
                .price(rs.getInt("price"))
                .stock(rs.getInt("stock"))
                .description(rs.getString("description"))
                .thumbnailUrl(rs.getString("thumbnail_url"))
                .viewCount(rs.getInt("view_count"))
                .availableFrom(rs.getTimestamp("available_from") != null ?
                        rs.getTimestamp("available_from").toLocalDateTime() : null)
                .createdBy(rs.getLong("created_by"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}

