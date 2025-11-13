package odyssey.backend.presentation.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequest {

    @NotBlank(message = "필수값입니다")
    private String password;

}
