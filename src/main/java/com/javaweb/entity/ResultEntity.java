package com.javaweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
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

    @Column(name = "readingPoint")
    private Integer readingPoint;

    @Column(name = "listeningPoint")
    private Integer listeningPoint;

    @Column(name = "readingCorrectAnswer")
    private Integer readingCorrectAnswer;

    @Column(name = "listeningCorrectAnswer")
    private Integer listeningCorrectAnswer;

    @Column(name = "complete_time")
    private Time completeTime;

    @Column(name = "type")
    private String type;

}
