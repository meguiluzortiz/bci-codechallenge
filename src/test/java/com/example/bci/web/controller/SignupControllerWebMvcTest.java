package com.example.bci.web.controller;

import com.example.bci.bussiness.repository.UserRepository;
import com.example.bci.bussiness.service.SignupService;
import com.example.bci.bussiness.service.impl.SignupServiceImpl;
import com.example.bci.web.request.Phone;
import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.SignupResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SignupController.class)
@Import({ SignupServiceImpl.class })
class SignupControllerWebMvcTest {

    @SpyBean
    SignupService signupService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnBadRequestWhenSignupWithEmptyRequest() throws Exception {
        var errors = new String[] {
                "username: must not be empty",
                "email: must not be empty",
                "password: must not be empty",
                "phones: must not be empty"
        };
        var request = SignupRequest.builder().build();

        performPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.errors").value(hasSize(errors.length)) )
                .andExpect(jsonPath("$.errors").value(hasItems(errors)));
    }

    @Test
    void shouldReturnBadRequestWhenSignupWithInvalidPasswordPattern() throws Exception {
        var password = "strong-password";
        var request = buildSignupRequest("example@example.com", password);
        var errors = new String[] { String.format("password: Value '%s' must accomplish pattern", password) };

        performPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors").value(hasSize(errors.length)) )
                .andExpect(jsonPath("$.errors", hasItems(errors)));
    }

    @Test
    void shouldReturnBadRequestWhenSignupWithValidRequestButExistingEmail() throws Exception {
        var email = "eatatjoes@acme.com";
        var request = buildSignupRequest(email, "1aA!4567");
        var errors = new String[] { String.format("Email %s already exists", email) };

        when(userRepository.existsByEmail(email)).thenReturn(true);

        performPost(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.errors").value(hasSize(errors.length)) )
                .andExpect(jsonPath("$.errors", hasItems(errors)));
    }

    @Test
    void shouldReturnInternalServerErrorWhenSignupWithValidRequestButAnErrorHappens() throws Exception {
        var request = buildSignupRequest("eatatjoes@acme.com", "1aA!4567");
        var errors = new String[] { "An error occurred" };

        doThrow(RuntimeException.class).when(signupService).signup(any(SignupRequest.class));

        performPost(request)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors").value(hasSize(errors.length)) )
                .andExpect(jsonPath("$.errors", hasItems(errors)));
    }

    @Test
    void shouldReturnSuccessWhenSignupWithValidRequest() throws Exception {
        var rightNow = LocalDate.now().toString();
        var response = SignupResponse.builder()
                .id(UUID.randomUUID().toString())
                .token(UUID.randomUUID().toString())
                .created(rightNow).lastLogin(rightNow)
                .isActive(true)
                .build();
        var request = buildSignupRequest("example@example.com", "1aA!4567");

        doReturn(response).when(signupService).signup(any(SignupRequest.class));

        performPost(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.created").value(is(rightNow)))
                .andExpect(jsonPath("$.lastLogin").value(is(rightNow)))
                .andExpect(jsonPath("$.isActive").value(is(true)));
    }

    ResultActions performPost(SignupRequest request) throws Exception {
        String content = objectMapper.writeValueAsString(request);
        return mockMvc.perform(post("/signup").content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    SignupRequest buildSignupRequest(String email, String password) {
        return SignupRequest.builder()
                .username("Joe")
                .email(email)
                .password(password)
                .phones(List.of(Phone.builder().number("1234567890").cityCode("LIM").countryCode("PE").build()))
                .build();
    }

}
