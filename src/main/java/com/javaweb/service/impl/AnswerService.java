package com.javaweb.service.impl;

import com.javaweb.entity.AnswerEntity;
import com.javaweb.repository.AnswerRepository;
import com.javaweb.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService implements IAnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public AnswerEntity findById(Long id) {
        return answerRepository.findById(id).get();
    }
}
