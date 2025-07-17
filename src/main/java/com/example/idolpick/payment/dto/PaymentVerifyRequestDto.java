package com.example.idolpick.payment.dto;

import com.example.idolpick.order.entity.Order;
import com.example.idolpick.user.entity.User;
import lombok.Getter;

@Getter
public class PaymentVerifyRequestDto {
    private String imp_uid;
    private String merchant_uid;
    private Integer expected_amount;
}
