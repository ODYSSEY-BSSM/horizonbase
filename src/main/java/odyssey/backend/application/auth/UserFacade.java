package odyssey.backend.application.auth;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.UserNotFoundException;
import odyssey.backend.infrastructure.persistence.auth.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getUserByUuid(Long uuid){
        return userRepository.findUserByUuid(uuid)
                .orElseThrow(UserNotFoundException::new);
    }

}
