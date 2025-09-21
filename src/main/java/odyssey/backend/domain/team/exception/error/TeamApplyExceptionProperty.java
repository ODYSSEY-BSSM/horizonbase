package odyssey.backend.domain.team.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TeamApplyExceptionProperty implements ErrorProperty {
    APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 신청입니다.");

    private final HttpStatus status;
    private final String message;
}
