package com.example.idolpick.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IdolLike {
    private Long id;
    private Long idolId;
    private Long userId;
    private LocalDateTime createdAt;
}
