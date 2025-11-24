package odyssey.backend.presentation.node.dto.response;

import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.problem.Problem;

public record SimpleNodeResponse(
        Long id,
        String title,
        String description,
        Integer problemCount,
        Long solverProblemCount
) {
    public static SimpleNodeResponse from(Node node) {
        return new SimpleNodeResponse(
                node.getId(),
                node.getTitle(),
                node.getDescription(),
                node.getProblems().size(),
                node.getProblems()
                        .stream()
                        .filter(Problem::isResolved)
                        .count()
        );
    }
}
