package com.example.bci.web.controller;

import com.example.bci.bussiness.service.SignupService;
import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.ErrorResponse;
import com.example.bci.web.response.SignupResponse;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BCI Endpoints", description = "BCI challenge endpoints")
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @Operation(summary = "Signup a new user", description = "Signup a new user when email is not registered yet", tags = { "BCI Endpoints" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true,
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignupResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))} )
    })
    @PostMapping(consumes = { APPLICATION_JSON_VALUE })
    public ResponseEntity<SignupResponse> signup(
            @Parameter(description = "A user to signup", required = true)
            @Valid @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(signupService.signup(request));
    }
}
