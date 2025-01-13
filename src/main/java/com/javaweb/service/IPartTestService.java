package com.javaweb.service;

import com.javaweb.entity.PartTestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPartTestService {

    List<PartTestEntity> findAllByTestEntityIsNull();

}
