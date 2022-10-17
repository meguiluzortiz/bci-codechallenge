package com.example.bci.bussiness.repository;

import com.example.bci.bussiness.entity.PhoneEntity;
import com.example.bci.bussiness.entity.UserEntity;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    Faker faker = new Faker();
    UserEntity lastEntity;

    final String randomId = UUID.randomUUID().toString();

    @Test
    void shouldCreateNewUser() {
        String email = faker.internet().emailAddress();
        LocalDateTime now = LocalDateTime.now();
        lastEntity = UserEntity.builder()
                .id(randomId)
                .name(faker.name().firstName())
                .email(email)
                .password(faker.internet().password(8, 12, true, true, true))
                .phones(List.of(
                        PhoneEntity.builder().countryCode("51").cityCode("01").number(faker.phoneNumber().cellPhone()).build()
                ))
                .created(now)
                .modified(now)
                .lastLogin(now)
                .isActive(true)
                .build();

        userRepository.saveAndFlush(lastEntity);
        assertThat(userRepository.existsByEmail(email)).isTrue();
    }

    @Test
    void shouldFindAllUsers() {
        var list = userRepository.findAll();
        assertThat(list).hasSize(1);
    }
}
