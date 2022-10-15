package com.example.bci.web.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @Schema(defaultValue = "Bad Request")
    private String message;

    @Schema(defaultValue = "Email eatatjoes@acme.com already exists")
    private List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.errors = List.of(error);
    }
}
