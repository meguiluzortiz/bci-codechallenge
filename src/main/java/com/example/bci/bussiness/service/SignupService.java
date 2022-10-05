package com.example.bci.bussiness.service;

import com.example.bci.web.request.SignupRequest;
import com.example.bci.web.response.SignupResponse;

public interface SignupService {

    SignupResponse signup(SignupRequest request);
}
