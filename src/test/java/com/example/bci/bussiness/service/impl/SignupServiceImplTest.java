package com.example.bci.bussiness.service.impl;

import com.example.bci.bussiness.exception.EmailAlreadyExistsException;
import com.example.bci.bussiness.repository.UserRepository;
import com.example.bci.web.request.Phone;
import com.example.bci.web.request.SignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupServiceImplTest {

    @InjectMocks
    SignupServiceImpl signupService;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldSignupWhenValidRequest() {
        var request = buildSignupRequest("example@example.com");

        var uuidPattern = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        var rightNow = LocalDateTime.now().toString();
        var response = signupService.signup(request);

        assertThat(response.getId(), is(notNullValue()));
        assertThat(response.getToken(), matchesPattern(uuidPattern) );
        assertThat(response.getCreated(), is(greaterThanOrEqualTo(rightNow)) );
        assertThat(response.getLastLogin(), is(greaterThanOrEqualTo(rightNow)) );
        assertThat(response.getIsActive(), is(true) );

    }

    @Test
    void shouldThrowExceptionWhenSignupWithValidRequestButExistingEmail() {
        var email = "eatatjoes@acme.com";
        var request = buildSignupRequest(email);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        EmailAlreadyExistsException thrown = Assertions.assertThrows(EmailAlreadyExistsException.class,
                () -> signupService.signup(request));

        assertThat(thrown, isA(EmailAlreadyExistsException.class));
        assertThat(thrown.getMessage(), is(String.format("Email %s already exists", email)));
    }

    SignupRequest buildSignupRequest(String email) {
        return SignupRequest.builder()
                .username("Joe")
                .email(email)
                .password("1aA!4567")
                .phones(List.of(Phone.builder().number("1234567890").cityCode("LIM").countryCode("PE").build()))
                .build();
    }
}
