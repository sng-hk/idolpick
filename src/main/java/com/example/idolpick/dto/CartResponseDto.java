package com.example.idolpick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponseDto {
    private List<CartItemResponseDto> cartItems;
    private Integer total;
}
