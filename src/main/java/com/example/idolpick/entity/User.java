package com.example.idolpick.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String phoneNumber;
    private String birthDate;

    // 회원가입용 생성자
    public User(String email, String nickname, String phoneNumber, String birthDate) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}

