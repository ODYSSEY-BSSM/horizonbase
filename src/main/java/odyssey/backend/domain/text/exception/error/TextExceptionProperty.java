package odyssey.backend.domain.text.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TextExceptionProperty implements ErrorProperty {
    TEXT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 텍스트데스");

    private final HttpStatus status;
    private final String message;
}
