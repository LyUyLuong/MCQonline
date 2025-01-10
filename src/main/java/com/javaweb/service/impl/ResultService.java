package com.javaweb.service.impl;

import com.javaweb.converter.ResultConverter;
import com.javaweb.converter.TestConverter;
import com.javaweb.converter.UserConverter;
import com.javaweb.entity.*;
import com.javaweb.enums.TestType;
import com.javaweb.model.dto.ResultDTO;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.FormRaw;
import com.javaweb.model.raw.UserAnswerRaw;
import com.javaweb.repository.PartTestRepository;
import com.javaweb.repository.ResultHavePartsRepository;
import com.javaweb.repository.ResultRepository;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IResultService;
import com.javaweb.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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

    @Autowired
    PartTestRepository partTestRepository;

    @Autowired
    ResultHavePartsRepository resultHavePartsRepository;

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
                                    List<String> parts) {
        UserDTO userDTO = userService.findOneByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        List<UserAnswerRaw> userAnswerList = form.getUserAnswerRawList();


        String testType = TestType.Full_Test.name();

        if (parts != null && !parts.isEmpty()) {
            testType = TestType.Parts_Test.name();
        }

        ResultEntity resultEntity = resultRepository.save(
                    resultConverter.toEntity(ResultDTO.builder()
                    .testEntity(testConverter.toEntity(testDTO))
                    .userEntity(userConverter.convertToEntity(userDTO))
                    .completeTime(TimeUtils.convertToTIme(form))
                    .type(testType)
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

        List<ResultHavePartsEntity> resultHavePartsEntities = new ArrayList<>();
        if (parts != null && !parts.isEmpty()) {
            for (String partId : parts) {
                PartTestEntity partTestEntity = partTestRepository.findById(Long.parseLong(partId))
                        .orElseThrow(() -> new IllegalArgumentException("Part not found with id: " + partId));

                ResultHavePartsEntity resultHavePartsEntity = new ResultHavePartsEntity();
                resultHavePartsEntity.setResult(resultEntity);
                resultHavePartsEntity.setPartTest(partTestEntity);

                resultHavePartsEntities.add(resultHavePartsEntity);
            }
        }
        else {
            for(PartTestEntity partTestEntity : testDTO.getPartTestEntities()){
                ResultHavePartsEntity resultHavePartsEntity = new ResultHavePartsEntity();
                resultHavePartsEntity.setResult(resultEntity);
                resultHavePartsEntity.setPartTest(partTestEntity);

                resultHavePartsEntities.add(resultHavePartsEntity);
            }
        }
        resultHavePartsRepository.saveAll(resultHavePartsEntities);


        userAnswerRepository.saveAll(userAnswerEntities);

        return resultEntity;

    }

    @Override
    public List<ResultEntity> getAllResultsByUserEntityAndId(UserEntity user,Long id) {

        List<ResultEntity> resultEntities = resultRepository.findAllByUserEntityAndId(user,id);

        return Collections.emptyList();
    }


}
