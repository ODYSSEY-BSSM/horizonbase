package odyssey.backend.presentation.auth.dto.response;

import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.user.dto.response.UserInfo;

public record SignUpResponse(
        UserInfo userInfo
) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                UserInfo.from(user)
        );
    }
}
