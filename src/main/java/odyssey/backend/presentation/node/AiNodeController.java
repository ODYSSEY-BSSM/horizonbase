package odyssey.backend.presentation.node;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.node.NodeService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.node.dto.response.NodeResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.ListCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AiNodeController {

    private final NodeService nodeService;

    @PostMapping("/directories/{directoryId}/nodes/ai")
    @ResponseStatus(HttpStatus.CREATED)
    public ListCommonResponse<NodeResponse> createAiNodes(
            @PathVariable Long directoryId,
            @RequestParam(required = false) Long teamId,
            @RequestBody @Valid GenerateRoadmapRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(nodeService.generageAiNodes(directoryId, teamId, request, user));
    }
}
