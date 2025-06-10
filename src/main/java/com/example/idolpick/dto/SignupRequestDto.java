package com.example.idolpick.dto;

import com.example.idolpick.entity.User;
import com.example.idolpick.entity.role.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
public class SignupRequestDto {
    private String email;
    private String nickname;
    private String birthDate;
    private String phoneNumber;
    private String role;

    public User toEntity() {
        LocalDate parsedBirthDate;
        try {
            parsedBirthDate = LocalDate.parse(this.birthDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("생년월일 형식이 잘못되었습니다. yyyy-MM-dd 형식이어야 합니다.");
        }

        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .birthDate(parsedBirthDate)
                .role(UserRole.valueOf(this.role))
                .build();
    }

}
