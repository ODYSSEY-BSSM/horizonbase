package odyssey.backend.presentation.user.dto.response;

import odyssey.backend.domain.auth.Role;
import odyssey.backend.domain.auth.User;

public record UserInfo(
        Long uuid,
        String username,
        String email,
        Role role
) {
    public static UserInfo from(User user){
        return new UserInfo(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
