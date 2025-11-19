package odyssey.backend.presentation.problem.dto.response;

import odyssey.backend.domain.problem.Problem;
import odyssey.backend.domain.problem.Status;

import java.util.List;

public record ProblemResponse(
        Long id,
        String title,
        List<String> choices,
        Status status
) {
    public static ProblemResponse from(Problem problem) {
        return new ProblemResponse(
                problem.getId(),
                problem.getTitle(),
                problem.getChoices(),
                problem.getStatus()
        );
    }
}
