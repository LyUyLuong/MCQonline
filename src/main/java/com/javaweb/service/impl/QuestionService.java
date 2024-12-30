package com.javaweb.service.impl;

import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.repository.QuestionRepository;
import com.javaweb.service.IQuestionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public QuestionTestEntity findById(Long id) {
        return questionRepository.findById(id).get();
    }
}
