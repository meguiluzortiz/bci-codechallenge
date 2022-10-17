package com.example.bci.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Test
    void shouldLoginWhenValidCredentials() {

        String mockJwt = "jwt";
        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(any())).thenReturn(mockJwt);

        String jwt = authenticationService.login("username", "password");

        assertThat(jwt, equalTo(mockJwt));
    }

}

