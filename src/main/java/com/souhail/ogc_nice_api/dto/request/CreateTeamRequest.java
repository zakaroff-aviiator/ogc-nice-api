package com.souhail.ogc_nice_api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateTeamRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String acronym;

    @NotNull
    @Positive
    private BigDecimal budget;

    // optional – can be null or empty
    @Valid
    private List<CreatePlayerRequest> players;
}
