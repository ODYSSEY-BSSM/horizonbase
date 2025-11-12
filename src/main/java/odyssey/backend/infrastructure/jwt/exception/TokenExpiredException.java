package odyssey.backend.infrastructure.jwt.exception;

import odyssey.backend.infrastructure.jwt.exception.error.JwtExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class TokenExpiredException extends GlobalException {
    public TokenExpiredException() {
        super(JwtExceptionProperty.TOKEN_EXPIRED);
    }
}
