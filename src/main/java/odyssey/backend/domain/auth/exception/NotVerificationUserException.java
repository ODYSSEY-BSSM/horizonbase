package odyssey.backend.domain.auth.exception;

import odyssey.backend.domain.auth.exception.error.SignUpVerificationExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class NotVerificationUserException extends GlobalException {
    public NotVerificationUserException() {
        super(SignUpVerificationExceptionProperty.NOT_VERIFICATION_USER);
    }
}
