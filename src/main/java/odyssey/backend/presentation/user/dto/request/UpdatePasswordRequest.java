package odyssey.backend.presentation.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotEmpty(message = "필수값입니다.")
    private String password;

}
