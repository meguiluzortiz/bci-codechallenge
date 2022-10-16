package com.example.bci.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Schema(defaultValue = "1234567890")
    @Size(max = 30, message = "The number must be between {min} and {max} characters long")
    private String number;

    @Schema(defaultValue = "01")
    @Size(max = 5, message = "The cityCode must be between {min} and {max} characters long")
    private String cityCode;

    @Schema(defaultValue = "51")
    @Size(max = 5, message = "The cityCode must be between {min} and {max} characters long")
    private String countryCode;
}
