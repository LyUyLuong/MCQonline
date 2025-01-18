package com.javaweb.converter;

import com.javaweb.entity.CourseEntity;
import com.javaweb.model.dto.CourseDTO;
import com.javaweb.model.response.CourseReponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CourseConverter {
    @Autowired
    ModelMapper modelMapper;

    public CourseReponse toReponse(CourseEntity courseEntity) {
        return modelMapper.map(courseEntity, CourseReponse.class);
    }

    public CourseDTO toDTO(CourseEntity courseEntity) {
        return modelMapper.map(courseEntity, CourseDTO.class);
    }

    public CourseEntity toEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, CourseEntity.class);
    }
}
