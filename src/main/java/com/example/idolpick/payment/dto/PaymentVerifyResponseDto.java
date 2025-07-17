package com.example.idolpick.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerifyResponseDto {
    private boolean success;
    private String message;

    // 기본 생성자, getter, setter 생략
    public static PaymentVerifyResponseDto success(String message) {
        PaymentVerifyResponseDto dto = new PaymentVerifyResponseDto();
        dto.setSuccess(true);
        dto.setMessage(message);
        return dto;
    }

    public static PaymentVerifyResponseDto fail(String message) {
        PaymentVerifyResponseDto dto = new PaymentVerifyResponseDto();
        dto.setSuccess(false);
        dto.setMessage(message);
        return dto;
    }
}
