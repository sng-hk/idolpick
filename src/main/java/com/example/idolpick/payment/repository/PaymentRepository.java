package com.example.idolpick.payment.repository;

import com.example.idolpick.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

//    public void save(Payment payment) {
//        String sql = """
//            INSERT INTO payment (
//                merchant_uid, imp_uid, payment_method, amount, status, paid_at
//            ) VALUES (?, ?, ?, ?, ?, ?)
//        """;
//
//        jdbcTemplate.update(sql,
//                payment.getMerchant_uid(),
//                payment.getImp_uid(),
//                payment.getPayment_method(),
//                payment.getAmount(),
//                payment.getStatus().name(), // enum -> String
//                Timestamp.valueOf(payment.getPaid_at()) // LocalDateTime -> Timestamp
//        );
//    }

    public void save(Payment payment) {
        String sql = """
            INSERT INTO payment (
            user_id, order_id, imp_uid, payment_method, total_amount, status, created_at
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql, payment.getUserId(), payment.getOrderId(), payment.getImp_uid(), payment.getPayment_method(), payment.getAmount(), payment.getStatus().name(), payment.getPaid_at());
    }
}
