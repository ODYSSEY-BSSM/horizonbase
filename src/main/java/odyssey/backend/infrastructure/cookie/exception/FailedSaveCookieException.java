package odyssey.backend.infrastructure.cookie.exception;

import odyssey.backend.infrastructure.cookie.exception.error.CookieExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class FailedSaveCookieException extends GlobalException {
    public FailedSaveCookieException() {
        super(CookieExceptionProperty.FAILED_SAVE_COOKIE);
    }
}
