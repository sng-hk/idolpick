package com.example.idolpick.repository;

import com.example.idolpick.entity.Category;
import com.example.idolpick.entity.User;
import com.example.idolpick.entity.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
