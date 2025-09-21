package odyssey.backend.domain.team.exception;

import odyssey.backend.domain.team.exception.error.TeamApplyExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class ApplyNotFoundException extends GlobalException {
    public ApplyNotFoundException() {
        super(TeamApplyExceptionProperty.APPLY_NOT_FOUND);
    }
}
