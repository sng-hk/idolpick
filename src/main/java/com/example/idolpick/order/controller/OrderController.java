package com.example.idolpick.order.controller;

import com.example.idolpick.order.dto.OrderDto;
import com.example.idolpick.order.entity.Order;
import com.example.idolpick.order.service.OrderService;
import com.example.idolpick.user.entity.User;
import com.example.idolpick.order.repository.OrderRepository;
import com.example.idolpick.order.repository.ShipAddressRepository;
import com.example.idolpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ShipAddressRepository shipAddressRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    // TODO : 기본 주소 및 사용자 선호 주소지 받아오기
//    public ResponseEntity<?> getShipAddress(Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("인증된 사용자가 아닙니다.");
//        }
//        Map<String, String> userInfo = (Map<String, String>) authentication.getPrincipal();
//        String email = userInfo.get("email");
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
//        return null;
//    }

    // 결제 전 주문 생성
    @PostMapping
    public ResponseEntity<OrderDto.OrderResponseDto> createOrder(@RequestBody OrderDto.OrderRequestDto orderRequestDto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("인증된 사용자가 아닙니다.");
        }
        Map<String, String> userInfo = (Map<String, String>) authentication.getPrincipal();
        String email = userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        OrderDto.OrderResponseDto order = orderService.createOrder(user.getId(), orderRequestDto);

        return ResponseEntity.ok(order);

    }

    @GetMapping
    public ResponseEntity<List<OrderDto.OrderReadResponseDto>> findOrder(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("인증된 사용자가 아닙니다.");
        }
        Map<String, String> userInfo = (Map<String, String>) authentication.getPrincipal();
        String email = userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        List<OrderDto.OrderReadResponseDto> order = orderService.findOrder(user.getId());

        return ResponseEntity.ok(order);
    }
}
