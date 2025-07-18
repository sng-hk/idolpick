package com.example.idolpick.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OrderItem {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Integer price;
}
