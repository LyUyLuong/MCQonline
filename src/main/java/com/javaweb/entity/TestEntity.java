package com.javaweb.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "test")
public class TestEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "testEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PartTestEntity> partTestEntities;

    @OneToMany(mappedBy = "testEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultEntity> resultEntities;

    @Column(name = "number_of_participants")
    private Long numberOfParticipants;

    @Column(name = "status")
    private Integer status;

}
