package com.example.idolpick.entity;

import com.example.idolpick.entity.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserRole role; // Enum

    // 회원가입용 생성자
    public User(String email, String nickname, String phoneNumber, LocalDate birthDate, UserRole role) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
    }
}

