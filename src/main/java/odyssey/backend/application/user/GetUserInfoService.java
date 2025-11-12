package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.auth.UserFacade;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final UserFacade userFacade;

    public UserResponse getUserInfo(User user) {
        return UserResponse.from(userFacade.getUserByEmail(user.getEmail()));
    }

}

