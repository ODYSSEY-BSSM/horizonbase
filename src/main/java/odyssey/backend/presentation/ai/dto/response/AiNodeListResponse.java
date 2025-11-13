package odyssey.backend.presentation.ai.dto.response;

import odyssey.backend.presentation.node.dto.response.NodeResponse;

import java.util.List;

public record AiNodeListResponse(
        List<NodeResponse> nodes
) {
}
