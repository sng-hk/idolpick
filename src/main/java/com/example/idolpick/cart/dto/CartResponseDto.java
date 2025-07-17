package com.example.idolpick.cart.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartResponseDto {
    private List<CartItemResponseDto> cartItems;
    private Integer total;

    public CartResponseDto(List<CartItemResponseDto> cartItems) {
        this.cartItems = cartItems;
        this.total = 0;
        for (CartItemResponseDto cartItem : cartItems) {
            this.total += cartItem.getTotal();
        }
    }
}
