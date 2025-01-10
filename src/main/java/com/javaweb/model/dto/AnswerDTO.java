package com.javaweb.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

    private String content;

    private Boolean correct;

    private String mark;

}
