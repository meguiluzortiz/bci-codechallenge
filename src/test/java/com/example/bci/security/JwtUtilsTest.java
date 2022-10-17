package com.example.bci.security;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    final String mockJwtToken = "jwtToken";
    final String mockUsername = "username";

    @Mock
    JwtProperties jwtProperties;

    @Mock
    JwtParser jwtParser;

    @InjectMocks
    JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        when(jwtProperties.getSecret()).thenReturn("secret");
    }

    @Test
    void shouldGenerateTokenWhenCalled() {
        when(jwtProperties.getExpiration()).thenReturn(5*60*1000);
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("test");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);
        assertThat(jwt, notNullValue());
    }

    @Test
    void shouldGetUserNameWhenValidToken() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(mockUsername);

        Jws<Claims> mockJwsClaims = mock(Jws.class);
        when(mockJwsClaims.getBody()).thenReturn(claims);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenReturn(mockJwsClaims);

        String username = jwtUtils.getUserNameFromJwtToken(mockJwtToken);
        assertThat(username, notNullValue());
        assertThat(username, is(mockUsername));
    }

    @Test
    void shouldValidateTokenWhenIsValid() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);

        Jws<Claims> mockJwsClaims = mock(Jws.class);
        when(mockJwsClaims.getBody()).thenReturn(null);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenReturn(mockJwsClaims);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(true));
    }

    @Test
    void shouldReturnFalseWhenSignatureExceptionIsThrown() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);

        when(jwtParser.parseClaimsJws(mockJwtToken)).thenThrow(SignatureException.class);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(false));
    }

    @Test
    void shouldReturnFalseWhenMalformedJwtExceptionIsThrown() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenThrow(MalformedJwtException.class);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(false));
    }

    @Test
    void shouldReturnFalseWhenExpiredJwtExceptionIsThrown() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenThrow(ExpiredJwtException.class);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(false));
    }

    @Test
    void shouldReturnFalseWhenUnsupportedJwtExceptionIsThrown() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenThrow(UnsupportedJwtException.class);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(false));
    }

    @Test
    void shouldReturnFalseWhenIllegalArgumentExceptionIsThrown() {
        when(jwtParser.setSigningKey(jwtProperties.getSecret())).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(mockJwtToken)).thenThrow(IllegalArgumentException.class);

        boolean validation = jwtUtils.validateJwtToken(mockJwtToken);
        assertThat(validation, notNullValue());
        assertThat(validation, equalTo(false));
    }
}
