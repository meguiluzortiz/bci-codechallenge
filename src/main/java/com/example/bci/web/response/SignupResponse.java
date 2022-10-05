package com.example.bci.web.response;

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
public class SignupResponse {
    private String id;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private Boolean isActive;
}
