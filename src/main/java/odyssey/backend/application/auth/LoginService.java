package odyssey.backend.application.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.InvalidPasswordException;
import odyssey.backend.domain.auth.service.FindUserService;
import odyssey.backend.infrastructure.cookie.CookieUtil;
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
    private final CookieUtil cookieUtil;

    public TokenResponse login(LoginRequest request, HttpServletResponse response) {
        User user = findUserService.findUserByEmail(request.getEmail());

        valid(request.getPassword(), user.getPassword());

        String accessToken = tokenService.generateAccessToken(user);

        cookieUtil.addCookie(response, "accessToken", accessToken, 60 * 60);

        String refreshToken = tokenService.generateRefreshToken(user);

        cookieUtil.addCookie(response, "refreshToken", refreshToken, 60 * 60 * 7 * 24);

        return TokenResponse.create(accessToken, refreshToken);
    }

    public void valid(String rawPassword, String encodedPassword) {
        if(!bCryptPasswordEncoder.matches(rawPassword, encodedPassword)){
            throw new InvalidPasswordException();
        }
    }
}
