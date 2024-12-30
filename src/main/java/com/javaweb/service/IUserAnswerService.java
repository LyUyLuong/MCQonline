package com.javaweb.service;

import com.javaweb.entity.AnswerEntity;
import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserAnswerService {

    List<UserAnswerEntity> getUserAnswers(ResultEntity resultEntity);

}
