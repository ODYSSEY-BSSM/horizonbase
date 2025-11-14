package odyssey.backend.presentation.ai.dto.response;

import java.util.List;

public record AiNodeListResponse(
        List<AiNodeResponse> nodes
) {
}
