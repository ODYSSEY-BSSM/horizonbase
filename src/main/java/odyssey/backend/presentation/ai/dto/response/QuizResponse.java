package odyssey.backend.presentation.ai.dto.response;

import odyssey.backend.presentation.ai.dto.response.vo.QuizInfo;

import java.util.List;

public record QuizResponse(
        List<QuizInfo> questions
) {
}
