package com.example.idolpick.product.controller;

import com.example.idolpick.product.dto.ProductRequestDto;
import com.example.idolpick.product.entity.Product;
import com.example.idolpick.user.entity.User;
import com.example.idolpick.product.repository.ProductRepository;
import com.example.idolpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/md/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Product>> product(Authentication authentication) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        List<Product> products = productRepository.findAllByCreatedBy(user.getId());

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public void createProduct(@RequestBody ProductRequestDto request, Authentication authentication) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Product product = request.toEntity(user.getId());
        productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id, Authentication authentication) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));
        if (product.getCreatedBy() != user.getId()) throw new IllegalArgumentException("해당 상품에 대한 접근 권한이 없습니다.");
        productRepository.delete(product.getId());
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto request, Authentication authentication) {
        Map<String, Object> userInfo = (Map<String, Object>) authentication.getPrincipal();
        String email = (String) userInfo.get("email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));
        if (product.getCreatedBy() != user.getId()) throw new IllegalArgumentException("해당 상품에 대한 접근 권한이 없습니다.");
            product = Product.builder()
                    .id(id)
                    .name(request.getName())
                    .categoryId(request.getCategoryId())
                    .price(request.getPrice())
                    .stock(request.getStock())
                    .description(request.getDescription())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .availableFrom(request.getAvailableFrom())
                    .createdBy(user.getId())
                    .build();

        productRepository.update(product);
    }
}
