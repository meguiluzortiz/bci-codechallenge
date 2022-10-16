package com.example.bci.web.request;

import com.example.bci.bussiness.util.Constants;
import com.example.bci.web.validator.PropertyPattern;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Schema(defaultValue = "mkyle")
    @NotEmpty
    @Size(max = 20, message = "The username '${validatedValue}' must be between {min} and {max} characters long")
    private String username;

    @Schema(defaultValue = "eatatkyles@acme.com", description = "Email 'eatatjoes@acme.com' is already registered")
    @NotEmpty
    @Pattern(regexp = Constants.PATTERN_EMAIL)
    @Size(min = 5, max = 80, message = "The email '${validatedValue}' must be between {min} and {max} characters long")
    private String email;

    @Schema(defaultValue = "1aA!4567", description = "Password must follow pattern in application.yml file")
    @NotEmpty
    @Size(min = 8, max = 16, message = "The password must be between {min} and {max} characters long")
    @PropertyPattern(property = "pattern.signup-request.password")
    private String password;

    @NotEmpty
    @Size(min = 1, message = "The phone list must contain at least {min} item")
    private List<Phone> phones;
}
