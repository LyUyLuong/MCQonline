package com.javaweb.api.web;

import com.javaweb.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartAPI {

    @Autowired
    CartService cartService;

    @PostMapping("/add/{courseId}")
    public ResponseEntity<?> addToCart(@PathVariable Long courseId) {
        cartService.addToCart(courseId);
        return ResponseEntity.ok("Đã thêm vào giỏ hàng!");
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.ok("Đã xóa khỏi giỏ hàng!");
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {
        cartService.checkout();
        return ResponseEntity.ok("Thanh toán thành công!");
    }
}
