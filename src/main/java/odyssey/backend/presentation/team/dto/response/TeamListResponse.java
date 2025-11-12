package odyssey.backend.presentation.team.dto.response;

import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;

import java.util.List;

public record TeamListResponse(
        List<String> teams
) {
    public static TeamListResponse from(User user) {
        return new TeamListResponse(
                user.getTeams()
                        .stream()
                        .map(Team::getName)
                        .toList()
        );
    }
}
