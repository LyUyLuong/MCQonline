package com.javaweb.repository;

import com.javaweb.entity.ResultEntity;
import com.javaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
    List<ResultEntity> findAllByUserEntityAndId(UserEntity user,Long id);
    List<ResultEntity> findAllByUserEntity(UserEntity user);
}
