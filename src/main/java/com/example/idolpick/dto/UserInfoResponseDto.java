package com.example.idolpick.dto;

import com.example.idolpick.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private String email;
    private String nickname;
    private String role;

    public UserInfoResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
    }
}
