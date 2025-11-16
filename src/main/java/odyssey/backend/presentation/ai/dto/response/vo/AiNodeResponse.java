package odyssey.backend.presentation.ai.dto.response.vo;

import odyssey.backend.domain.node.NodeType;

public record AiNodeResponse(
        Long id,
        String title,
        String description,
        NodeType type,
        int x,
        int y,
        Long parentNodeId,
        boolean isEducation
) {
}
