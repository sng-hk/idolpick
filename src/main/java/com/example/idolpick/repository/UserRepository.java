package com.example.idolpick.repository;

import com.example.idolpick.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        // 이메일이 없는 경우가 자연스럽기 때문에 try catch로 감싸서 처리
        String sql = "SELECT email, nickname, phone_number, birth_date FROM user WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("nickname"),
                            rs.getString("phone_number"),
                            rs.getString("birth_date")
                    ), email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    public User save(User user) {
        String sql = "INSERT INTO user (email, nickname, phone_number, birth_date) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getNickname());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getBirthDate());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new User(generatedId, user.getEmail(), user.getNickname(), user.getPhoneNumber(), user.getBirthDate());
    }

}
