package com.souhail.ogc_nice_api.service;

import com.souhail.ogc_nice_api.dto.request.CreateTeamRequest;
import com.souhail.ogc_nice_api.dto.response.TeamResponseDto;
import org.springframework.data.domain.Page;

public interface TeamService {

    Page<TeamResponseDto> getTeams(int page, int size, String sortBy, String direction);

    TeamResponseDto createTeam(CreateTeamRequest request);

    TeamResponseDto getTeamById(Long id);

    void deleteTeam(Long id);
}
