package odyssey.backend.presentation.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @Email(message = "이메일 형식이어야합니다")
    @NotEmpty(message = "필수값입니다.")
    private String email;

    @NotEmpty(message = "필수값입니다.")
    private String password;

}
