package com.javaweb.converter;


import com.javaweb.entity.AnswerEntity;
import com.javaweb.model.dto.AnswerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerConverter {

    @Autowired
    ModelMapper modelMapper;

    public AnswerDTO toDTO(AnswerEntity answer){
        AnswerDTO dto = modelMapper.map(answer, AnswerDTO.class);
        return dto;
    }

}
