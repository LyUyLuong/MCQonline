package com.javaweb.service.impl;

import com.javaweb.converter.ResultConverter;
import com.javaweb.converter.TestConverter;
import com.javaweb.converter.UserConverter;
import com.javaweb.entity.*;
import com.javaweb.model.dto.ResultDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.FormRaw;
import com.javaweb.model.raw.UserAnswerRaw;
import com.javaweb.repository.ResultRepository;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Autowired
    UserService userService;

    @Override
    public List<ResultEntity> getAllResultsByUserEntity (UserEntity userEntity) {
        return resultRepository.findAllByUserEntity(userEntity);
    }

    @Override
    public ResultEntity getOneResult(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    @Override
    public ResultEntity createOrUpdateResultEntity(ResultEntity resultEntity) {
        return null;
    }

    @Override
    public ResultEntity sumbitSheet(TestDTO testDTO,
                                    FormRaw form,
                                    String typeTest) {
        UserDTO userDTO = userService.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        List<UserAnswerRaw> userAnswerList = form.getUserAnswerRawList();
//        System.out.println(Time.valueOf(form.getCompleteTime()));

        Integer hours = Integer.valueOf(form.getCompleteTime()) / 3600;
        Integer minutes = Integer.valueOf(form.getCompleteTime()) / 60;
        Integer seconds = Integer.valueOf(form.getCompleteTime()) % 60;

        Time time = Time.valueOf(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        ResultEntity resultEntity = resultRepository.save(
                    resultConverter.toEntity(ResultDTO.builder()
                    .testEntity(testConverter.toEntity(testDTO))
                    .userEntity(userConverter.convertToEntity(userDTO))
                    .completeTime(time)
                            .type(typeTest)
                    .build()));

        List<UserAnswerEntity> userAnswerEntities = new ArrayList<>();

        Integer listeningCorrectAnswer = 0;
        Integer readingCorrectAnswer = 0;
        Integer listeningPoints = 0;
        Integer readingPoints = 0;


        for (UserAnswerRaw userAnswerRaw : userAnswerList) {
            UserAnswerEntity userAnswerEntity = new UserAnswerEntity();

            QuestionTestEntity questionTestEntity = questionService.findById(userAnswerRaw.getQuestionId());
            AnswerEntity answerEntity = answerService.findById(userAnswerRaw.getAnswerId());

            userAnswerEntity.setQuestionTestEntity(questionTestEntity);
            userAnswerEntity.setAnswerEntity(answerEntity);
            userAnswerEntity.setResultEntity(resultEntity);

            userAnswerEntities.add(userAnswerEntity);
            String partType = questionService.findById(userAnswerRaw.getQuestionId()).getPartTest().getPartType();
            switch (partType) {
                case "PART_1":
                case "PART_2":
                case "PART_3":
                case "PART_4":
                    if(answerService.findById(userAnswerRaw.getAnswerId()).getCorrect()){
                        listeningCorrectAnswer+=1;
                    }
                    break;
                case "PART_5":
                case "PART_6":
                case "PART_7":
                    if(answerService.findById(userAnswerRaw.getAnswerId()).getCorrect()){
                        readingCorrectAnswer+=1;
                    }
                    break;
            }


        }

        if(readingCorrectAnswer>2){
            readingPoints = (readingCorrectAnswer-1)*5;
        }
        else readingPoints=5;

        if(listeningCorrectAnswer>0){
            listeningPoints = (listeningCorrectAnswer)*5+10;
        }
        else readingPoints=5;

        resultEntity.setListeningPoint(listeningPoints);
        resultEntity.setReadingPoint(readingPoints);
        resultEntity.setListeningCorrectAnswer(listeningCorrectAnswer);
        resultEntity.setReadingCorrectAnswer(readingCorrectAnswer);


        userAnswerRepository.saveAll(userAnswerEntities);

        return resultEntity;
//        return new ResultEntity();
    }

    @Override
    public List<ResultEntity> getAllResultsByUserEntityAndId(UserEntity user,Long id) {

        List<ResultEntity> resultEntities = resultRepository.findAllByUserEntityAndId(user,id);

        return Collections.emptyList();
    }


}
