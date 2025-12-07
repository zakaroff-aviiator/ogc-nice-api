package com.souhail.ogc_nice_api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlayerResponseDto {
    Long id;
    String name;
    String position;
}
