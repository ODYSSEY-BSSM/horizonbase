package odyssey.backend.domain.auth.exception;

import odyssey.backend.domain.auth.exception.error.SignUpVerificationExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InvalidVerificationCodeException extends GlobalException {
    public InvalidVerificationCodeException() {
        super(SignUpVerificationExceptionProperty.INVALID_VERIFICATION_CODE);
    }
}
