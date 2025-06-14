package com.example.idolpick.dto;

import lombok.Getter;

@Getter
public class CartUpdateRequestDto {
    private Long productId;
    private Integer quantity;
}
