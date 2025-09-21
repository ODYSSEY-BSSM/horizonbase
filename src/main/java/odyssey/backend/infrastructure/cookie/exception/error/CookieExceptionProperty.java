package odyssey.backend.infrastructure.cookie.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CookieExceptionProperty implements ErrorProperty {
    FAILED_SAVE_COOKIE(HttpStatus.INTERNAL_SERVER_ERROR, "쿠키 저장에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
