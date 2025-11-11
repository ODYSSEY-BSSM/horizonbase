package odyssey.backend.presentation.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.auth.LoginService;
import odyssey.backend.application.auth.LogoutService;
import odyssey.backend.application.auth.RefreshService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.infrastructure.jwt.dto.response.TokenResponse;
import odyssey.backend.presentation.auth.dto.request.LoginRequest;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;
    private final LogoutService logoutService;
    private final RefreshService refreshService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<TokenResponse> login(
            @RequestBody @Valid LoginRequest request
    ){
        return CommonResponse.ok(loginService.login(request));
    }

    @PutMapping("/token")
    public SingleCommonResponse<TokenResponse> refreshAccessToken(
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        return CommonResponse.ok(refreshService.refreshToken(refreshToken));
    }

    @DeleteMapping
    public SingleCommonResponse<String> logout(
            @AuthenticationPrincipal User user
    ) {
        logoutService.logout(user);
        return CommonResponse.ok("로그아웃되었습니다.");
    }

}
