package com.javaweb.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class AnswerEntity extends BaseEntity{

    @Column(name = "answer_version", nullable = false)
    private Integer answerVersion = 1;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionTestEntity questionTestEntity;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_correct", nullable = false)
    private Boolean correct;

    @Column(name = "mark", nullable = false)
    private String mark;
}
