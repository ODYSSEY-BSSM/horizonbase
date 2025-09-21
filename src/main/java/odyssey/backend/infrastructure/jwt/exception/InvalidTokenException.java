package odyssey.backend.infrastructure.jwt.exception;

import odyssey.backend.infrastructure.jwt.exception.error.JwtExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InvalidTokenException extends GlobalException {
    public InvalidTokenException() {
        super(JwtExceptionProperty.INVALID_TOKEN_EXCEPTION);
    }
}
