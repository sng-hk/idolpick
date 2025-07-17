package com.example.idolpick.product.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private Long idolId;
    private String idolName;
    private String name;
    private String categoryName;  // categoryId를 실제 이름으로 매핑해줄 수도 있음
    private Integer price;
    private Integer stock;
    private String description;
    private String thumbnailUrl;
    private Integer viewCount;
    private LocalDateTime availableFrom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

