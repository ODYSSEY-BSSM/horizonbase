package odyssey.backend.infrastructure.ai.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import odyssey.backend.shared.exception.ErrorProperty;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AiExceptionProperty implements ErrorProperty {
    FAILED_GENERATE_RESPONSE_FROM_AI(HttpStatus.INTERNAL_SERVER_ERROR, "AI 응답 생성에 실패하였습니다");

    private final HttpStatus status;
    private final String message;
}
