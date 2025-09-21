package odyssey.backend.infrastructure.jwt.exception;

import odyssey.backend.infrastructure.jwt.exception.error.JwtExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class TokenNotFoundException extends GlobalException {
    public TokenNotFoundException() {
        super(JwtExceptionProperty.TOKEN_NOT_FOUND);
    }
}
