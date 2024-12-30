package com.javaweb.service.impl;

import com.javaweb.converter.ResultConverter;
import com.javaweb.converter.TestConverter;
import com.javaweb.converter.UserConverter;
import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.ResultDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.UserAnswerRaw;
import com.javaweb.repository.ResultRepository;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ResultService implements IResultService {
    @Autowired
    ResultRepository resultRepository;

    @Autowired
    TestConverter testConverter;

    @Autowired
    UserConverter userConverter;

    @Autowired
    ResultConverter resultConverter;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @Override
    public List<ResultEntity> getAllResultsByUserEntity (UserEntity userEntity) {
        return Collections.emptyList();
    }

    @Override
    public ResultEntity getOneResult(Long id) {
        return resultRepository.findById(id).get();
    }

    @Override
    public ResultEntity createOrUpdateResultEntity(ResultEntity resultEntity) {
        return null;
    }

    @Override
    public ResultEntity sumbitSheet(UserDTO userDTO, TestDTO testDTO,List<UserAnswerRaw> userAnswerList) {

        ResultEntity resultEntity = resultRepository.save(
                resultConverter.toEntity(ResultDTO.builder()
                .testEntity(testConverter.toEntity(testDTO))
                .userEntity(userConverter.convertToEntity(userDTO))
                .build()));
        resultEntity.setTestEntity(testConverter.toEntity(testDTO));
        resultEntity.setUserEntity(userConverter.convertToEntity(userDTO));


        List<UserAnswerEntity> userAnswerEntities = new ArrayList<>();

        for (UserAnswerRaw userAnswerRaw : userAnswerList) {
            UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
            userAnswerEntity.setQuestionTestEntity(questionService.findById(userAnswerRaw.getQuestionId()));
            userAnswerEntity.setAnswerEntity(answerService.findById(userAnswerRaw.getAnswerId()));
            userAnswerEntity.setResultEntity(resultEntity);

            userAnswerEntities.add(userAnswerEntity);
        }


        userAnswerRepository.saveAll(userAnswerEntities);

        return resultEntity;
    }
}
