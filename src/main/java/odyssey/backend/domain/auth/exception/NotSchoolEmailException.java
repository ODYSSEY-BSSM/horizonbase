package odyssey.backend.domain.auth.exception;

import odyssey.backend.domain.auth.exception.error.UserExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class NotSchoolEmailException extends GlobalException {
    public NotSchoolEmailException() {
        super(UserExceptionProperty.NOT_SCHOOL_EMAIL);
    }
}
