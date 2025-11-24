package odyssey.backend.domain.text.exception;

import odyssey.backend.domain.text.exception.error.TextExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class TextNotFoundException extends GlobalException {
    public TextNotFoundException() {
        super(TextExceptionProperty.TEXT_NOT_FOUND);
    }
}
