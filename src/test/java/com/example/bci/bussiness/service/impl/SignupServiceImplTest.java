package com.example.bci.bussiness.service.impl;

import com.example.bci.bussiness.exception.EmailAlreadyExistsException;
import com.example.bci.bussiness.service.SignupService;
import com.example.bci.web.request.Phone;
import com.example.bci.web.request.SignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class SignupServiceImplTest {

    @InjectMocks
    SignupServiceImpl signupService;

    @Test
    void shouldSignupWhenValidRequest() {
        var request = buildSignupRequest("example@example.com");

        var uuidPattern = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        var rightNow = LocalDate.now();
        var response = signupService.signup(request);

        assertThat(response.getId(), is(notNullValue()));
        assertThat(response.getToken(), matchesPattern(uuidPattern) );
        assertThat(response.getCreated(), is(greaterThanOrEqualTo(rightNow)) );
        assertThat(response.getLastLogin(), is(greaterThanOrEqualTo(rightNow)) );
        assertThat(response.getIsActive(), is(true) );

    }

    @Test
    void shouldThrowExceptionWhenSignupWithValidRequestButExistingEmail() {
        var email = "eatatjoes@example.com";
        var request = buildSignupRequest(email);

        EmailAlreadyExistsException thrown = Assertions.assertThrows(EmailAlreadyExistsException.class,
                () -> signupService.signup(request));

        assertThat(thrown, isA(EmailAlreadyExistsException.class));
        assertThat(thrown.getMessage(), is(String.format("Email %s already exists", email)));
    }

    SignupRequest buildSignupRequest(String email) {
        return SignupRequest.builder()
                .name("Joe")
                .email(email)
                .password("1aA!4567")
                .phones(List.of(Phone.builder().number("1234567890").cityCode("LIM").countryCode("PE").build()))
                .build();
    }
}
