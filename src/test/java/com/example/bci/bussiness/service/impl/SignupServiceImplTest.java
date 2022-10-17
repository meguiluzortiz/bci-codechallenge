package com.example.bci.bussiness.service.impl;

import com.example.bci.bussiness.exception.EmailAlreadyExistsException;
import com.example.bci.bussiness.repository.UserRepository;
import com.example.bci.security.AuthenticationService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignupServiceImplTest {

    @InjectMocks
    SignupServiceImpl signupService;

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationService authenticationService;

    @Test
    void shouldSignupWhenValidRequest() {
        var request = buildSignupRequest("example@example.com");
        var token = "jwt";
        var rightNow = LocalDateTime.now().toString();

        when(authenticationService.login(anyString(), anyString())).thenReturn(token);

        var response = signupService.signup(request);

        assertThat(response.getId(), is(notNullValue()));
        assertThat(response.getToken(), is(equalTo(token)) );
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
                .name("name")
                .email(email)
                .password("password")
                .phones(List.of(Phone.builder().number("1234567890").cityCode("LIM").countryCode("PE").build()))
                .build();
    }
}
