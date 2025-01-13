package com.javaweb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "part_test")
public class PartTestEntity extends BaseEntity {

    @Column(name = "part_type", nullable = false)
    private String partType;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = true)
    @JsonIgnore
    private TestEntity testEntity;

    @OneToMany(mappedBy = "partTest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionTestEntity> questions;

    @OneToMany(mappedBy = "partTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultHavePartsEntity> resultHaveParts;

}
