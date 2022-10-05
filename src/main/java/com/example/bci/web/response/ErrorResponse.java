package com.example.bci.web.response;

import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private List<String> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.errors = List.of(error);
    }
}
