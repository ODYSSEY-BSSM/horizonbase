package odyssey.backend.domain.auth.exception;

import odyssey.backend.domain.auth.exception.error.SignUpVerificationExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InvalidRequestEmailException extends GlobalException {
    public InvalidRequestEmailException() {
        super(SignUpVerificationExceptionProperty.INVALID_REQUEST_EMAIL);
    }
}
