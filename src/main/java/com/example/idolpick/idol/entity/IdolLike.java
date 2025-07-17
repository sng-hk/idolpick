package com.example.idolpick.idol.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IdolLike {
    private Long id;
    private Long idolId;
    private Long userId;
    private LocalDateTime createdAt;
}
