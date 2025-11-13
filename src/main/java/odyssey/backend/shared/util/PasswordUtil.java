package odyssey.backend.shared.util;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.exception.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    public void validate(String rawPassword, String encodedPassword) {
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            throw new InvalidPasswordException();
        }
    }

}
