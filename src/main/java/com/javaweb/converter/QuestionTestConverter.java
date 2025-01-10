package com.javaweb.converter;

import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.model.dto.QuestionTestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionTestConverter {

    @Autowired
    ModelMapper modelMapper;

    public QuestionTestDTO toDTO(QuestionTestEntity questionTestEntity) {
        return modelMapper.map(questionTestEntity, QuestionTestDTO.class);
    }

}
