package odyssey.backend.presentation.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.user.ConnectSchoolUseCase;
import odyssey.backend.application.user.GetUserInfoService;
import odyssey.backend.application.user.SignUpService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.auth.dto.response.SignUpResponse;
import odyssey.backend.presentation.user.dto.request.SignUpRequest;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final SignUpService signUpService;
    private final GetUserInfoService getUserInfoService;
    private final ConnectSchoolUseCase  connectSchoolUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleCommonResponse<SignUpResponse> signUp(
            @RequestBody @Valid SignUpRequest signUpRequest
    ) {
        return CommonResponse.ok(signUpService.signUp(signUpRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<UserResponse> getUserInfo(
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(getUserInfoService.getUserInfo(user));
    }

    @PutMapping("/school")
    @ResponseStatus(HttpStatus.OK)
    public SingleCommonResponse<UserResponse> connectSchool(
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(connectSchoolUseCase.ConnectSchool(user));
    }

}
