package com.example.bci.web.controller;

import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.ErrorResponse;
import com.example.bci.web.response.SignupResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "BCI Endpoints", description = "BCI challenge endpoints")
@Slf4j
@RestController
public class HelloWorldController {

    @Operation(summary = "Authorized endpoint", description = "Authorized endpoint for testing security", tags = { "BCI Endpoints" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true,
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignupResponse.class)) })
    })
    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello world");
    }

}
