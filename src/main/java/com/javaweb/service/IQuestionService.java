package com.javaweb.service;

import com.javaweb.entity.QuestionTestEntity;

public interface IQuestionService {
    QuestionTestEntity findById(Long id);
}
