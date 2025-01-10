package com.javaweb.service;

import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.model.response.QuestionDetailResponse;

public interface IQuestionService {
    QuestionTestEntity findById(Long id);

    QuestionDetailResponse findDetailByQuestionIdAndResultId(Long id, Long userId);
}
