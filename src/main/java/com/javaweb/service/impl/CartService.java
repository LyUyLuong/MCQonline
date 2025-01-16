package com.javaweb.service.impl;

import com.javaweb.entity.*;
import com.javaweb.repository.*;
import com.javaweb.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Thêm khóa học vào giỏ hàng
     */
    public ResponseEntity<?> addToCart(Long courseId) {
        // Get or create the cart for the user
        CartEntity cart = getOrCreateCart();

        // Check if the course exists
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khóa học với ID: " + courseId));

        // Check if the course is already in the cart
        boolean isCourseInCart = cart.getCartItems().stream()
                .anyMatch(item -> item.getCourse().getId().equals(courseId));

        if (isCourseInCart) {
            // Return a response if the course is already in the cart
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Khóa học đã tồn tại trong giỏ hàng!");
        }

        // Add a new item to the cart
        CartItemEntity newItem = new CartItemEntity();
        newItem.setCourse(course);
        newItem.setQuantity(1); // Always set quantity to 1 since duplicates are not allowed
        newItem.setPrice(course.getPrice());
        newItem.setCart(cart);

        cart.getCartItems().add(newItem);
        cartItemRepository.save(newItem);

        // Update the total price of the cart
        updateCartTotal(cart);

        // Return a success response
        return ResponseEntity.ok("Đã thêm khóa học vào giỏ hàng!");
    }


    /**
     * Xóa mục khỏi giỏ hàng
     */
    public void removeFromCart(Long itemId) {
        // Lấy mục giỏ hàng
        CartItemEntity item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mục giỏ hàng với ID: " + itemId));

        // Lấy giỏ hàng chứa mục này
        CartEntity cart = item.getCart();

        // Xóa mục giỏ hàng
        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);

        // Cập nhật tổng giá trị của giỏ hàng
        updateCartTotal(cart);
    }

    /**
     * Xử lý thanh toán
     */
    @Transactional
    public void checkout() {
        // Lấy giỏ hàng hiện tại
        CartEntity cart = getOrCreateCart();

        // Kiểm tra giỏ hàng có rỗng không
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Giỏ hàng rỗng, không thể thanh toán.");
        }

        // Tạo một đơn hàng mới
        OrderEntity order = new OrderEntity();
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setUser(userRepository.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));

        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (CartItemEntity cartItem : cart.getCartItems()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCourse(cartItem.getCourse());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        // Lưu đơn hàng
        orderRepository.save(order);

        // Xóa giỏ hàng sau khi thanh toán
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    /**
     * Lấy danh sách mục giỏ hàng
     */
    public List<CartItemEntity> getCartItems() {
        CartEntity cart = getOrCreateCart();
        return new ArrayList<>(cart.getCartItems());
    }

    /**
     * Lấy giỏ hàng hiện tại
     */
    public CartEntity getCart() {
        return getOrCreateCart();
    }

    /**
     * Lấy giỏ hàng hoặc tạo mới nếu chưa có
     */
    private CartEntity getOrCreateCart() {

        UserEntity user = userRepository.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user == null) {
            return null;
        }

        // Giả sử giỏ hàng liên kết với người dùng, ở đây tạm sử dụng User ID là 1
        Long userId = userRepository.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(userRepository.getOne(userId));
                    newCart.setTotalPrice(0.0);
                    cartRepository.save(newCart);
                    return newCart;
                });
    }

    /**
     * Cập nhật tổng giá trị của giỏ hàng
     */
    private void updateCartTotal(CartEntity cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}