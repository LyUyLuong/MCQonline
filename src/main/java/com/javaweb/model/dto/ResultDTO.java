package com.javaweb.model.dto;

import com.javaweb.entity.BaseEntity;
import com.javaweb.entity.TestEntity;
import com.javaweb.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class ResultDTO extends BaseEntity {

    private UserEntity userEntity;

    private TestEntity testEntity;

    private String type;

    private Time completeTime;

}
