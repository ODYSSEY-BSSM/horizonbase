package odyssey.backend.presentation.node.dto.response;

import odyssey.backend.domain.node.Node;

public record SimpleNodeResponse(
        Long id,
        String title,
        String description
) {
    public static SimpleNodeResponse from(Node node) {
        return new SimpleNodeResponse(
                node.getId(),
                node.getTitle(),
                node.getDescription()
        );
    }
}
