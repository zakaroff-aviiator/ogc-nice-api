package com.souhail.ogc_nice_api.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class TeamResponseDto {
    Long id;
    String name;
    String acronym;
    BigDecimal budget;
    List<PlayerResponseDto> players;
}
