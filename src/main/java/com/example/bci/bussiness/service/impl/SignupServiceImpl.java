package com.example.bci.bussiness.service.impl;

import com.example.bci.bussiness.exception.EmailAlreadyExistsException;
import com.example.bci.bussiness.repository.UserRepository;
import com.example.bci.bussiness.service.SignupService;
import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.SignupResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {
    private AtomicInteger id = new AtomicInteger(1);

    private final UserRepository userRepository;

    @Override
    public SignupResponse signup(SignupRequest request) {
        String email = request.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.info("Email {} already exists", email);
            throw new EmailAlreadyExistsException(String.format("Email %s already exists", email));
        }

        LocalDate rightNow = LocalDate.now();
        return SignupResponse.builder()
                .id(id.getAndIncrement())
                .token(UUID.randomUUID().toString())
                .created(rightNow).lastLogin(rightNow)
                .isActive(true)
                .build();
    }
}
