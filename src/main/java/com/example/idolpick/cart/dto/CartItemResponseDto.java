package com.example.idolpick.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer total;
}
