package com.souhail.ogc_nice_api.mapper;

import com.souhail.ogc_nice_api.dto.request.CreatePlayerRequest;
import com.souhail.ogc_nice_api.dto.request.CreateTeamRequest;
import com.souhail.ogc_nice_api.dto.response.PlayerResponseDto;
import com.souhail.ogc_nice_api.dto.response.TeamResponseDto;
import com.souhail.ogc_nice_api.entity.Player;
import com.souhail.ogc_nice_api.entity.Team;

import java.util.List;

public final class TeamMapper {

    private TeamMapper() {
    }

    public static Team toEntity(CreateTeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .budget(request.getBudget())
                .build();

        if (request.getPlayers() != null) {
            List<Player> players = request.getPlayers()
                    .stream()
                    .map(p -> toPlayerEntity(p, team))
                    .toList();
            team.setPlayers(players);
        }

        return team;
    }

    public static Player toPlayerEntity(CreatePlayerRequest request, Team team) {
        return Player.builder()
                .name(request.getName())
                .position(request.getPosition())
                .team(team)
                .build();
    }

    public static TeamResponseDto toDto(Team team) {
        List<PlayerResponseDto> players = team.getPlayers()
                .stream()
                .map(TeamMapper::toDto)
                .toList();

        return TeamResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .acronym(team.getAcronym())
                .budget(team.getBudget())
                .players(players)
                .build();
    }

    public static PlayerResponseDto toDto(Player player) {
        return PlayerResponseDto.builder()
                .id(player.getId())
                .name(player.getName())
                .position(player.getPosition())
                .build();
    }
}
