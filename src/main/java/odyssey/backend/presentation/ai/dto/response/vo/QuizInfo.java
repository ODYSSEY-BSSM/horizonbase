package odyssey.backend.presentation.ai.dto.response.vo;

import java.util.List;

public record QuizInfo(
        String title,
        List<String> choices,
        Integer correct
) {
}
