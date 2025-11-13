package odyssey.backend.presentation.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.user.*;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.auth.dto.request.DeleteUserRequest;
import odyssey.backend.presentation.auth.dto.response.SignUpResponse;
import odyssey.backend.presentation.user.dto.request.SignUpRequest;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordRequest;
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
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

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

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request){
        updatePasswordUseCase.updatePassword(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody DeleteUserRequest request
    ){
        deleteUserUseCase.execute(user, request);
    }

}
