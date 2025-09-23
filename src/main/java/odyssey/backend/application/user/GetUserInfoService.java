package odyssey.backend.application.user;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.service.FindUserService;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final FindUserService findUserService;

    public UserResponse getUserInfo(User user) {
        return UserResponse.from(findUserService.findUserByEmail(user.getEmail()));
    }

}

