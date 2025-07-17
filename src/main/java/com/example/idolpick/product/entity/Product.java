package com.example.idolpick.product.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;
    private Long idolId;
    private String name;
    private Long categoryId;
    private Integer price;
    private Integer stock;
    private String description;
    private String thumbnailUrl;
    private Integer viewCount;
    private LocalDateTime availableFrom;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}