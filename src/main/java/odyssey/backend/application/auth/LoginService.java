package odyssey.backend.application.auth;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.infrastructure.jwt.dto.response.TokenResponse;
import odyssey.backend.infrastructure.jwt.service.TokenService;
import odyssey.backend.presentation.auth.dto.request.LoginRequest;
import odyssey.backend.shared.util.PasswordUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserFacade userFacade;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final PasswordUtil passwordUtil;

    public TokenResponse login(LoginRequest request) {
        User user = userFacade.getUserByEmail(request.getEmail());

        valid(request.getPassword(), user.getPassword());

        return TokenResponse.create(
                tokenService.generateAccessToken(user),
                tokenService.generateRefreshToken(user)
        );
    }

    public void valid(String rawPassword, String encodedPassword) {
        passwordUtil.validate(rawPassword, encodedPassword);
    }
}
