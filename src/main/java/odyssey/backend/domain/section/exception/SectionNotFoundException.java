package odyssey.backend.domain.section.exception;

import odyssey.backend.domain.section.exception.error.SectionExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class SectionNotFoundException extends GlobalException {
    public SectionNotFoundException() {
        super(SectionExceptionProperty.SECTION_NOT_FOUND);
    }
}
