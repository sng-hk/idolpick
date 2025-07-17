package com.example.idolpick.idol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class IdolWithLikeResponseDto {
    private Long id;
    private String name;
    private String description;
    private String thumbnailUrl;
    private String imageUrl;
    private boolean liked;
}
