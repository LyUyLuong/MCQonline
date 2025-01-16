package com.javaweb.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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
    private Date orderDate;
}
