package com.example.idolpick.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long userId;
    private Integer totalPrice;
    // 주문 번호
    private String merchantUid;
    private String address;
    private String receiverName;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
