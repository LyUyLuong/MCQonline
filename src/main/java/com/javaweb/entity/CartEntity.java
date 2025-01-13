package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CartEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    // Tổng tiền trong giỏ hàng
    @Column(name = "total_price")
    private Double totalPrice;

    // Trạng thái của giỏ hàng (chưa thanh toán, đã thanh toán)
    @Column(name = "status")
    private String status; // "PENDING", "COMPLETED"
}
