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

    @Column(name = "part_type")
    private String partType;

    @Column(name = "part_name")
    private String partName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    @JsonIgnore
    private TestEntity testEntity;

    @OneToMany(mappedBy = "partTest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionTestEntity> questions;

    @OneToMany(mappedBy = "partTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultHavePartsEntity> resultHaveParts;

}
