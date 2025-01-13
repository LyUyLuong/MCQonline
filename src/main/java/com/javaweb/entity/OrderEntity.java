package com.javaweb.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status; // "PENDING", "COMPLETED", "CANCELED"

    @Column(name = "payment_method")
    private String paymentMethod; // "CASH", "ONLINE"

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}
