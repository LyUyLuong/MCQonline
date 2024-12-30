package com.javaweb.service;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.TestDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.raw.UserAnswerRaw;

import java.util.List;

public interface IResultService {

    List<ResultEntity> getAllResultsByUserEntity(UserEntity userEntityserId);
    ResultEntity getOneResult(Long id);
    ResultEntity createOrUpdateResultEntity(ResultEntity resultEntity);
    ResultEntity sumbitSheet(UserDTO userDTO, TestDTO testDTO,List<UserAnswerRaw> userAnswerList);

}
