package odyssey.backend.domain.team.exception;

import odyssey.backend.domain.team.exception.error.TeamExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class NotLeaderException extends GlobalException {
    public NotLeaderException() {
        super(TeamExceptionProperty.IS_NOT_LEADER);
    }
}
