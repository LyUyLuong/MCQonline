package com.javaweb.repository;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity,Long> {

    List<UserAnswerEntity> findAllByResultEntity(ResultEntity resultEntity);

    UserAnswerEntity findByQuestionTestEntity_IdAndResultEntity_Id(Long questionId, Long resultId);
}
