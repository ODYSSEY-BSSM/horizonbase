package odyssey.backend.infrastructure.jwt.exception;

import odyssey.backend.infrastructure.jwt.exception.error.JwtExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class InvalidTokenTypeException extends GlobalException {
    public InvalidTokenTypeException() {
        super(JwtExceptionProperty.INVALID_TOKEN_TYPE);
    }
}
