package com.example.bci.web.response;

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
public class SignupResponse {

    @Schema(defaultValue = "34a3b952-1892-4ebb-ba07-6735d9bcefd5")
    private String id;

    @Schema(defaultValue = "2022-10-05T16:08:01.860805")
    private String created;

    @Schema(defaultValue = "2022-10-05T16:08:01.860805")
    private String modified;

    @Schema(defaultValue = "2022-10-05T16:08:01.860805")
    private String lastLogin;

    @Schema(defaultValue = "jwt-token")
    private String token;

    @Schema(defaultValue = "true")
    private Boolean isActive;
}
