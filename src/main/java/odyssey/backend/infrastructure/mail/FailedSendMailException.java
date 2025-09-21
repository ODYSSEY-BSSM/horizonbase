package odyssey.backend.infrastructure.mail;

import odyssey.backend.infrastructure.mail.exception.error.MailExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class FailedSendMailException extends GlobalException {
    public FailedSendMailException() {
        super(MailExceptionProperty.FAILED_SEND_MAIL);
    }
}
