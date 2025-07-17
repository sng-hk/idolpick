package com.example.idolpick.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long userId;
    private Long orderId;
    private String imp_uid;
    private String payment_method;
    private Integer amount;
    private PaymentStatus status;
    private LocalDateTime paid_at;
}
