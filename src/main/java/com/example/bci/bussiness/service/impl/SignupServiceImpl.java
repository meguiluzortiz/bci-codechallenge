package com.example.bci.bussiness.service.impl;

import com.example.bci.bussiness.entity.PhoneEntity;
import com.example.bci.bussiness.entity.UserEntity;
import com.example.bci.bussiness.exception.EmailAlreadyExistsException;
import com.example.bci.bussiness.repository.UserRepository;
import com.example.bci.bussiness.service.SignupService;
import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.SignupResponse;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;

    @Override
    public SignupResponse signup(SignupRequest request) {
        String email = request.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.info("Email {} already exists", email);
            throw new EmailAlreadyExistsException(String.format("Email %s already exists", email));
        }

        var entity = buildEntity(request);
        userRepository.save(entity);
        log.debug("Entity created with id: {}", entity.getId());

        return SignupResponse.builder()
                .id(entity.getId())
                .token(UUID.randomUUID().toString())
                .created(entity.getCreated().toString()).lastLogin(entity.getLastLogin().toString())
                .isActive(entity.getIsActive())
                .build();
    }

    private UserEntity buildEntity(SignupRequest request) {
        var email = request.getEmail();
        var rightNow = LocalDateTime.now();
        return UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username(request.getUsername())
                .email(email)
                .password(request.getPassword())
                .phones(request.getPhones()
                        .stream()
                        .map(e -> PhoneEntity.builder()
                                .countryCode(e.getCountryCode())
                                .cityCode(e.getCityCode())
                                .number(e.getNumber())
                                .build()
                        ).collect(Collectors.toList()))
                .created(rightNow)
                .lastLogin(rightNow)
                .build();
    }
}
