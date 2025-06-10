package com.example.idolpick.dto;

import com.example.idolpick.entity.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private Long categoryId;
    private Integer price;
    private Integer stock;
    private String description;
    private String thumbnailUrl;
    private LocalDateTime availableFrom;
    private Long createdBy;  // 로그인 정보에서 꺼내는 게 보통이긴 함

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .categoryId(this.categoryId)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .thumbnailUrl(this.thumbnailUrl)
                .availableFrom(this.availableFrom)
                .createdBy(this.createdBy)
                .build();
    }
}

