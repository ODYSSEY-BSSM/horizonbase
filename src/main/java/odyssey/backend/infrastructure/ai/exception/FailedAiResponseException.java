package odyssey.backend.infrastructure.ai.exception;

import odyssey.backend.infrastructure.ai.exception.error.AiExceptionProperty;
import odyssey.backend.shared.exception.GlobalException;

public class FailedAiResponseException extends GlobalException {
    public FailedAiResponseException() {
        super(AiExceptionProperty.FAILED_GENERATE_RESPONSE_FROM_AI);
    }
}
