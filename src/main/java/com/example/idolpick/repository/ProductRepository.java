package com.example.idolpick.repository;

import com.example.idolpick.dto.GoodsResponseDto;
import com.example.idolpick.entity.Product;
import com.example.idolpick.repository.rowmapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper rowMapper = new ProductRowMapper();

    public List<Product> findAllByCreatedBy(Long userId) {
        String sql = "SELECT * FROM product WHERE created_by = ? ORDER BY name ASC";
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }


    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Optional<GoodsResponseDto> findGoodsById(Long id) {
        String increaseViewCountSql = "UPDATE product SET view_count = view_count + 1 WHERE id = ?";
        jdbcTemplate.update(increaseViewCountSql, id);

        String sql = """
                SELECT p.id,
                       p.name,
                       p.category_id, 
                       c.name as category_name,
                       p.price, 
                       p.stock,
                       p.description, 
                       p.thumbnail_url, 
                       p.view_count 
                       FROM product p       
                       LEFT JOIN category c ON p.category_id = c.id
                       WHERE p.id = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new GoodsResponseDto(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getLong("category_id"),
                            rs.getString("category_name"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("description"),
                            rs.getString("thumbnail_url"),
                            rs.getInt("view_count")
                    )
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // ROLE_ADMIN 전용
    public Optional<List<Product>> findAll() {
        String sql = "SELECT * FROM product ORDER BY created_at DESC";
        return Optional.ofNullable(jdbcTemplate.query(sql, rowMapper));
    }

    public void save(Product product) {
        String sql = """
            INSERT INTO product (name, idol_id, category_id, price, stock, description, thumbnail_url, view_count, available_from, created_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                product.getName(),
                product.getIdolId(),
                product.getCategoryId(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getThumbnailUrl(),
                product.getViewCount() != null ? product.getViewCount() : 0,
                product.getAvailableFrom(),
                product.getCreatedBy()
        );
    }

    public int update(Product product) {
        String sql = """
               UPDATE product
               SET name = ?,
               category_id = ?,
               price = ?,
               stock = ?,
               description = ?,
               thumbnail_url = ?,                  
               available_from = ?,
               updated_at = NOW()
               WHERE id = ? AND created_by = ? 
               """;
        return jdbcTemplate.update(sql,
                product.getName(),
                product.getCategoryId(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getThumbnailUrl(),
                product.getAvailableFrom(),
                product.getId(),
                product.getCreatedBy()
        );
    }

    // 특정 아이돌 상품만 찾기
    public List<GoodsResponseDto> findAllByIdolId(Long idolId) {
        String sql = """
                SELECT p.id,
                       p.name,
                       p.category_id, 
                       c.name as category_name,
                       p.price, 
                       p.stock,
                       p.description, 
                       p.thumbnail_url, 
                       p.view_count 
                       FROM product p       
                       LEFT JOIN category c ON p.category_id = c.id
                       WHERE p.idol_id = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{idolId}, (rs, rowNum) ->
                new GoodsResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("category_id"),
                        rs.getString("category_name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getString("thumbnail_url"),
                        rs.getInt("view_count")
                )
        );
    }
}
