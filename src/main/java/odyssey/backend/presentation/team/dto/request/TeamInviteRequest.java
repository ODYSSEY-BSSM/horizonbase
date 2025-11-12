package odyssey.backend.presentation.team.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamInviteRequest {

    @NotEmpty(message = "필수값입니다.")
    private String inviteCode;

}
