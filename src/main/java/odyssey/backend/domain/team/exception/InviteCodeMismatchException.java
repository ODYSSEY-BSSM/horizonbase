package odyssey.backend.domain.team.exception;

import odyssey.backend.domain.team.exception.error.TeamExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InviteCodeMismatchException extends GlobalException {
    public InviteCodeMismatchException() {
        super(TeamExceptionProperty.INVITECODE_MISMATCH);
    }
}
