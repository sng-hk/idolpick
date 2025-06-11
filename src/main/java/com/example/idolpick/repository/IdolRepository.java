package com.example.idolpick.repository;

import com.example.idolpick.dto.IdolWithLikeResponseDto;
import com.example.idolpick.entity.Idol;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdolRepository {
    private final JdbcTemplate jdbcTemplate;

   /* public List<Idol> findAll() {
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
    }*/
    /*public List<Idol> findByNameLike(String name) {
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
    }*/
    public List<IdolWithLikeResponseDto> findAllWithLike(Long userId, String name) { // admin>
        String sql = """
                SELECT
                    i.id,
                    i.name,
                    i.description,
                    i.image_url,
                    i.created_at,
                    i.updated_at,
                    CASE WHEN il.user_id IS NOT NULL THEN true ELSE false END AS liked
                FROM
                    idol i
                LEFT JOIN
                    idol_like il
                ON
                    i.id = il.idol_id AND il.user_id = ?
                WHERE
                    i.name LIKE ?
                """;
        return jdbcTemplate.query(sql, new Object[]{userId, "%" + name + "%"}, (rs, rowNum) -> IdolWithLikeResponseDto.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .imageUrl(rs.getString("image_url"))
                .liked(rs.getBoolean("liked"))
                .build());
    }


    public List<IdolWithLikeResponseDto> findAllWithLike(Long userId) {
        String sql = """
                SELECT
                    i.id,
                    i.name,
                    i.description,
                    i.image_url,
                    i.created_at,
                    i.updated_at,
                    CASE WHEN il.user_id IS NOT NULL THEN true ELSE false END AS liked
                FROM
                    idol i
                LEFT JOIN
                    idol_like il
                ON
                    i.id = il.idol_id AND il.user_id = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> IdolWithLikeResponseDto.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .imageUrl(rs.getString("image_url"))
                .liked(rs.getBoolean("liked"))
                .build());
    }

    public Optional<Idol> findById(Long idolId) {
        String sql = "SELECT * FROM idol WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Idol(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ), idolId));
    }
    public void likeIdol(Long userId, Long idolId) {
        // 이미 좋아요 등록되어 있지 않으면 insert
        if (!isLiked(userId, idolId)) {
            jdbcTemplate.update("INSERT INTO idol_like (user_id, idol_id) VALUES (?, ?)", userId, idolId);
        }
    }

    public void unlikeIdol(Long userId, Long idolId) {
        jdbcTemplate.update("DELETE FROM idol_like WHERE user_id = ? AND idol_id = ?", userId, idolId);
    }

    public boolean isLiked(Long userId, Long idolId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM idol_like WHERE user_id = ? AND idol_id = ?", // 존재 여부만 확인. 굳이 필요없는 정보까지 끌고 올 필요 없음
                Integer.class, userId, idolId
        );
        return count != null && count > 0;
    }
}