package com.javaweb.service.impl;

import com.javaweb.converter.AnswerConverter;
import com.javaweb.converter.QuestionTestConverter;
import com.javaweb.entity.AnswerEntity;
import com.javaweb.entity.QuestionTestEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.model.dto.AnswerDTO;
import com.javaweb.model.dto.QuestionTestDTO;
import com.javaweb.model.response.QuestionDetailResponse;
import com.javaweb.repository.QuestionRepository;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IQuestionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    AnswerConverter answerConverter;

    @Autowired
    QuestionTestConverter questionTestConverter;

    @Override
    public QuestionTestEntity findById(Long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public QuestionDetailResponse findDetailByQuestionIdAndResultId(Long questionId, Long resultId) {

        QuestionTestEntity question = questionRepository.findById(questionId).get();


        List<AnswerDTO> answers = new ArrayList<>();

        for(AnswerEntity answer : question.getAnswerEntityList()) {
            AnswerDTO dto  = answerConverter.toDTO(answer);
            answers.add(dto);
        }

        UserAnswerEntity userAnswerEntity = userAnswerRepository.findByQuestionTestEntity_IdAndResultEntity_Id(questionId, resultId);
        AnswerDTO userAnswer = null;

        if (userAnswerEntity != null && userAnswerEntity.getAnswerEntity() != null) {
            userAnswer = answerConverter.toDTO(userAnswerEntity.getAnswerEntity());
        }
        return new QuestionDetailResponse(questionTestConverter.toDTO(question), answers, userAnswer);
    }



}
