package com.example.idolpick.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql = "SELECT * FROM category ORDER BY name ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Category(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }
}
