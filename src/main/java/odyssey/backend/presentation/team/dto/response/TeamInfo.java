package odyssey.backend.presentation.team.dto.response;

import odyssey.backend.domain.team.Team;

public record TeamInfo(
        Long id,
        String name
) {
    public static TeamInfo from(Team team){
        return new TeamInfo(
                team.getId(),
                team.getName()
        );
    }
}
