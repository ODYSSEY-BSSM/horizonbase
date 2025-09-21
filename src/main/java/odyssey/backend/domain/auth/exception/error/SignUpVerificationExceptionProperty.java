package odyssey.backend.domain.auth.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SignUpVerificationExceptionProperty implements ErrorProperty {
    INVALID_REQUEST_EMAIL(HttpStatus.UNAUTHORIZED, "인증요청을 보내지 않은 이메일입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "잘못된 코드입니다."),
    NOT_VERIFICATION_USER(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다.");

    private final HttpStatus status;
    private final String message;

}
