package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "result_have_parts")
public class ResultHavePartsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "result_id", nullable = false)
    private ResultEntity result;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private PartTestEntity partTest;
}
