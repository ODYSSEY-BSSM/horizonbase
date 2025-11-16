package odyssey.backend.presentation.ai.dto.response;

import odyssey.backend.presentation.ai.dto.response.vo.AiModifyNodeResponse;

import java.util.List;

public record ModifyNodeResponse(
        List<AiModifyNodeResponse> nodes
) {
}
