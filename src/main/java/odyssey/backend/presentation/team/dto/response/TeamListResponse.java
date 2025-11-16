package odyssey.backend.presentation.team.dto.response;

import odyssey.backend.domain.team.Team;

import java.util.List;


public record TeamListResponse(
        List<TeamInfo> teams
) {
    public static TeamListResponse from(List<Team> teamList) {
        return new TeamListResponse(
                teamList
                        .stream()
                        .map(TeamInfo::from)
                        .toList()
        );
    }
}
