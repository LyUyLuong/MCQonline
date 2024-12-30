package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "result")
public class ResultEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private TestEntity testEntity;

    @Column(name = "score")
    private Integer score;

    @Column(name = "complete_tine")
    private Date completeTine;

}
