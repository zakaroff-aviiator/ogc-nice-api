package com.souhail.ogc_nice_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePlayerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String position;
}
