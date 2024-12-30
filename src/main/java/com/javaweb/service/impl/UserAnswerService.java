package com.javaweb.service.impl;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.repository.UserAnswerRepository;
import com.javaweb.service.IUserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserAnswerService implements IUserAnswerService {

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Override
    public List<UserAnswerEntity> getUserAnswers(ResultEntity resultEntity) {

        return userAnswerRepository.findAllByResultEntity(resultEntity);
    }
}
