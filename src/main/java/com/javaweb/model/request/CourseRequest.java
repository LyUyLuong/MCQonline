package com.javaweb.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {

    private String name;

    private String description;

    private Double price;

    private String category;

    private String status;


}
