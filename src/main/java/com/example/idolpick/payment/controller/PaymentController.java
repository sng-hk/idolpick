package com.example.idolpick.payment.controller;

import com.example.idolpick.order.service.OrderService;
import com.example.idolpick.payment.dto.PaymentVerifyResponseDto;
import com.example.idolpick.order.entity.Order;
import com.example.idolpick.payment.dto.PaymentVerifyRequestDto;
import com.example.idolpick.payment.entity.PaymentStatus;
import com.example.idolpick.payment.repository.PaymentRepository;
import com.example.idolpick.user.entity.User;
import com.example.idolpick.order.repository.OrderRepository;
import com.example.idolpick.user.repository.UserRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final IamportClient iamportClient;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @PostMapping("/verify")
    public ResponseEntity<PaymentVerifyResponseDto> verifyPayment(
            @RequestBody PaymentVerifyRequestDto req,
            Authentication authentication) {
        try {
            // 1. 아임포트 서버에서 결제 정보 조회
            IamportResponse<Payment> response = iamportClient.paymentByImpUid(req.getImp_uid());
            Payment payment = response.getResponse();

            if (payment.getAmount().intValue() == req.getExpected_amount()
                    && "paid".equals(payment.getStatus())) { // 결제 검증 성공 로직

                // 2. 사용자 인증 정보로부터 유저 찾기
                Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
                String email = (String) userInfo.get("email");
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

                // 4. 결제 성공에 따른 주문 상태 업데이트
                Order order = orderRepository.updateOrderStatus(req.getMerchant_uid(), "PAID");

                // 5. 결제 정보 저장 (선택적으로 Payment 테이블에 저장 가능)
                paymentRepository.save(new com.example.idolpick.payment.entity.Payment(
                        null,
                        user.getId(),
                        order.getId(),
                        payment.getImpUid(),
                        payment.getPgProvider(),
                        payment.getAmount().intValue(),
                        PaymentStatus.valueOf(payment.getStatus().toUpperCase()),
                        payment.getPaidAt().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                ));

                // 6. 상품 재고 감소
                orderService.updateProductStock(order.getId());

                return ResponseEntity.ok(PaymentVerifyResponseDto.success("결제 검증 성공"));

            } else {
                return ResponseEntity
                        .badRequest()
                        .body(PaymentVerifyResponseDto.fail("결제 검증 실패 또는 상태 비정상"));
            }

        } catch (IamportResponseException | IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(PaymentVerifyResponseDto.fail("아임포트 통신 실패"));
        }
    }
}