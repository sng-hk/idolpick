package com.example.idolpick.repository;

import com.example.idolpick.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT email, nickname, phone_number, birth_date FROM user WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new User(
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("phone_number"),
                        rs.getString("birth_date")
                ), email);
        return Optional.ofNullable(user);
    }

}
