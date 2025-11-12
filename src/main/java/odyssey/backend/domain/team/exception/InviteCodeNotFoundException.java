package odyssey.backend.domain.team.exception;

import odyssey.backend.domain.team.exception.error.TeamExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InviteCodeNotFoundException extends GlobalException {
    public InviteCodeNotFoundException() {
        super(TeamExceptionProperty.INVITECODE_NOT_FOUND);
    }
}
