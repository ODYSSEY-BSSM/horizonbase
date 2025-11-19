package odyssey.backend.presentation.ai.dto.response.vo;

import odyssey.backend.domain.node.NodeType;

public record AiModifyNodeResponse(
        Long id,
        String title,
        String description,
        NodeType type,
        Integer x,
        Integer y,
        Long parentId,
        Boolean isEducation
) {
}
