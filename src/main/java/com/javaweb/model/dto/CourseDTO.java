package com.javaweb.model.dto;

import com.javaweb.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Getter
@Setter
public class CourseDTO extends AbstractDTO{

    private String name;
    private String description;
    private Double price;
    private String category;
    private String image; // Đường dẫn hình ảnh khóa học
    private String imageBase64;
    private String imageName;
    private String status;

}
