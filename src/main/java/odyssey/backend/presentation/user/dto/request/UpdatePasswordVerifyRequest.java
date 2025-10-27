package odyssey.backend.presentation.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordVerifyRequest {

    @NotEmpty(message = "필수값입니다.")
    private String email;

    @NotEmpty(message = "필수값입니다.")
    private String code;

}
