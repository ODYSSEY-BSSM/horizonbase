package odyssey.backend.infrastructure.jwt.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionProperty implements ErrorProperty {
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "잘못된 토큰타입입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.");

    private final HttpStatus status;
    private final String message;
}
