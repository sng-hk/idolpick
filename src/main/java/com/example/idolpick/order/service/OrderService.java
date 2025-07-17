package com.example.idolpick.order.service;

import com.example.idolpick.cart.service.CartService;
import com.example.idolpick.order.dto.OrderDto;
import com.example.idolpick.order.entity.OrderItem;
import com.example.idolpick.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public OrderDto.OrderResponseDto createOrder(Long userId, OrderDto.OrderRequestDto orderRequestDto) {
        String merchantUid = generateMerchantUid();
        int totalAmount = 0;

        // 주문 항목 구성
        List<OrderItem> items = new ArrayList<>();
        for (OrderDto.OrderRequestDto.OrderItemDto itemDto : orderRequestDto.getItems()) {
            int price = orderRepository.getProductPrice(itemDto.getProductId());
            totalAmount += price * itemDto.getQuantity();
            items.add(new OrderItem(itemDto.getProductId(), itemDto.getQuantity(), price));
        }

        // 주문 저장
        Long orderId = orderRepository.createOrder(userId, merchantUid, totalAmount); // 주문 생성
        orderRepository.insertOrderItems(orderId, items); // 생성된 주문 내 주문 항목 저장

        // 장바구니 비우기
        cartService.truncateCart(userId);

        return new OrderDto.OrderResponseDto(merchantUid, totalAmount);
    }


    public List<OrderDto.OrderReadResponseDto> findOrder(Long userId) {
        return orderRepository.findOrdersByUser(userId);
    }

    // 주문 번호 생성
    private String generateMerchantUid() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return date + uuid;
    }
}
