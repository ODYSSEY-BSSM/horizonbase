package odyssey.backend.presentation.ai.dto.response;

import odyssey.backend.presentation.ai.dto.response.vo.AiNodeResponse;

import java.util.List;

public record AiNodeListResponse(
        List<AiNodeResponse> nodes
) {
}
