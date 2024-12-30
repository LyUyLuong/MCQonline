package com.javaweb.repository;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity,Long> {

    List<UserAnswerEntity> findAllByResultEntity(ResultEntity resultEntity);

}
