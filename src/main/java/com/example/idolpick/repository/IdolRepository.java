package com.example.idolpick.repository;

import com.example.idolpick.entity.Idol;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IdolRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Idol> findAll() {
        String sql = "SELECT * FROM idol ORDER BY name ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Idol(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getObject("created_at", LocalDateTime.class),
                        rs.getObject("updated_at", LocalDateTime.class)
                )
        );
    }

    public List<Idol> findByNameLike(String name) {
        String sql = "SELECT * FROM idol WHERE name LIKE ? ORDER BY name";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Idol(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ),
                "%" + name + "%"
        );
    }
}
