package odyssey.backend.application.auth;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.InvalidPasswordException;
import odyssey.backend.domain.auth.service.FindUserService;
import odyssey.backend.infrastructure.jwt.dto.response.TokenResponse;
import odyssey.backend.infrastructure.jwt.service.TokenService;
import odyssey.backend.presentation.auth.dto.request.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final FindUserService findUserService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public TokenResponse login(LoginRequest request) {
        User user = findUserService.findUserByEmail(request.getEmail());

        valid(request.getPassword(), user.getPassword());

        String accessToken = tokenService.generateAccessToken(user);

        String refreshToken = tokenService.generateRefreshToken(user);

        return TokenResponse.create(
                tokenService.generateAccessToken(user),
                tokenService.generateRefreshToken(user)
        );
    }

    public void valid(String rawPassword, String encodedPassword) {
        if(!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)){
            throw new InvalidPasswordException();
        }
    }
}
