package odyssey.backend.infrastructure.mail.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MailExceptionProperty implements ErrorProperty {
    FAILED_SEND_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다. 다시 시도해주십시오.");

    private final HttpStatus status;
    private final String message;
}
