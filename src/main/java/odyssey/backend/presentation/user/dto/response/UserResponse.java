package odyssey.backend.presentation.user.dto.response;

import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.team.Team;

import java.util.List;

public record UserResponse(
        UserInfo userInfo,
        List<String> teams,
        String school,
        Boolean isConnectedSchool
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                UserInfo.from(user),
                user.getTeams()
                        .stream()
                        .map(Team::getName)
                        .toList(),
                user.getSchool(),
                user.getIsConnectedSchool()
        );
    }
}
