package com.example.idolpick.repository;

import com.example.idolpick.entity.Category;
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

    public void delete(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<List<Product>> findAll() {
        String sql = "SELECT * FROM product ORDER BY created_at DESC";
        return Optional.ofNullable(jdbcTemplate.query(sql, rowMapper));
    }

    public void save(Product product) {
        String sql = """
            INSERT INTO product (name, category_id, price, stock, description, thumbnail_url, view_count, available_from, created_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                product.getName(),
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
}
