package com.javaweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question_test")
public class QuestionTestEntity extends BaseEntity {

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    @JsonBackReference
    private PartTestEntity partTest;

    @OneToMany(mappedBy = "questionTestEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerEntity> answerEntityList;

}
