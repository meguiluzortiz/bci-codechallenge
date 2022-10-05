package com.example.bci.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Schema(defaultValue = "1234567890")
    private String number;

    @Schema(defaultValue = "01")
    private String cityCode;

    @Schema(defaultValue = "51")
    private String countryCode;
}
