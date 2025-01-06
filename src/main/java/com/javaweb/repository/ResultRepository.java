package com.javaweb.repository;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
    List<ResultEntity> findAllByUserEntityAndId(UserEntity user,Long id);
    List<ResultEntity> findAllByUserEntity(UserEntity user);
}
