package com.souhail.ogc_nice_api.service.impl;

import com.souhail.ogc_nice_api.dto.request.CreateTeamRequest;
import com.souhail.ogc_nice_api.dto.response.TeamResponseDto;
import com.souhail.ogc_nice_api.entity.Team;
import com.souhail.ogc_nice_api.mapper.TeamMapper;
import com.souhail.ogc_nice_api.repository.TeamRepository;
import com.souhail.ogc_nice_api.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.souhail.ogc_nice_api.exception.TeamNotFoundException;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TeamResponseDto> getTeams(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        // protect against invalid sort fields by whitelisting
        if (!"name".equalsIgnoreCase(sortBy) &&
                !"acronym".equalsIgnoreCase(sortBy) &&
                !"budget".equalsIgnoreCase(sortBy)) {
            sortBy = "name";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        Page<Team> teamPage = teamRepository.findAll(pageable);

        return teamPage.map(TeamMapper::toDto);
    }

    @Override
    @Transactional
    public TeamResponseDto createTeam(CreateTeamRequest request) {
        Team team = TeamMapper.toEntity(request);
        Team saved = teamRepository.save(team);
        return TeamMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponseDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return TeamMapper.toDto(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        teamRepository.delete(team);
    }

}

