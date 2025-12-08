package com.souhail.ogc_nice_api.controller;

import com.souhail.ogc_nice_api.dto.request.CreateTeamRequest;
import com.souhail.ogc_nice_api.dto.response.TeamResponseDto;
import com.souhail.ogc_nice_api.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public Page<TeamResponseDto> getTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return teamService.getTeams(page, size, sortBy, direction);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDto createTeam(@Valid @RequestBody CreateTeamRequest request) {
        return teamService.createTeam(request);
    }

    @GetMapping("/{id}")
    public TeamResponseDto getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

}
