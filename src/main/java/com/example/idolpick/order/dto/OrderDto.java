package com.example.idolpick.order.dto;

import com.example.idolpick.order.entity.Order;
import com.example.idolpick.payment.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Getter
    public static class OrderRequestDto {
        private String receiverName;
        private String address;
        private String phone;
        private Integer totalPrice;
        private List<OrderItemDto> items;

        @Getter
        public static class OrderItemDto {
            private Long productId;
            private int quantity;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class OrderResponseDto {
        private String merchantUid;
        private int totalPrice;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class OrderReadResponseDto {
        private Long orderId;
        private String merchantUid;
        private int totalAmount;
        private PaymentStatus status;
        private LocalDateTime createdAt;
        private List<OrderItemReadResponseDto> items; // <OrderItemReadResponseDto>
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderItemReadResponseDto {
        private Long productId;
        private String thumbnailUrl;
        private String productName;
        private int priceAtOrder;
        private int quantity;
    }
}
