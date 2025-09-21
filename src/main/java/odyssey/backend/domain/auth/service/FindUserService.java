package odyssey.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.UserNotFoundException;
import odyssey.backend.infrastructure.persistence.auth.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;

    public User findUserByUuid(Long uuid) {
        return userRepository.findUserByUuid(uuid)
                .orElseThrow(UserNotFoundException::new);
    }

}
