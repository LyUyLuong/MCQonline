package com.javaweb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "part_test")
public class PartTestEntity extends BaseEntity {

    @Column(name = "part_type")
    private String partType;

    @Column(name = "part_name")
    private String partName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private TestEntity testEntity;

    @OneToMany(mappedBy = "partTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionTestEntity> questions;

}
