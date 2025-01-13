package com.javaweb.service.impl;

import com.javaweb.entity.PartTestEntity;
import com.javaweb.repository.PartTestRepository;
import com.javaweb.service.IPartTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PartTestService implements IPartTestService {

    @Autowired
    PartTestRepository partTestRepository;

    @Override
    public List<PartTestEntity> findAllByTestEntityIsNull() {
        return partTestRepository.findAllByTestEntityIsNull();
    }
}
