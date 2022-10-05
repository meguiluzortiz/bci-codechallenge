package com.example.bci.web.controller;

import com.example.bci.bussiness.service.SignupService;
import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.SignupResponse;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@AllArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(signupService.signup(request));
    }
}
