package com.example.idolpick.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoodsResponseDto {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;  // categoryId를 실제 이름으로 매핑해줄 수도 있음
    private Integer price;
    private Integer stock;
    private String description;
    private String thumbnailUrl;
    private Integer viewCount;
}
