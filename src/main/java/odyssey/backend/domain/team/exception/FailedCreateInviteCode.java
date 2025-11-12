package odyssey.backend.domain.team.exception;

import odyssey.backend.domain.team.exception.error.TeamExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class FailedCreateInviteCode extends GlobalException {
    public FailedCreateInviteCode() {super(TeamExceptionProperty.FAILED_CREATE_INVITE_CODE);}
}
