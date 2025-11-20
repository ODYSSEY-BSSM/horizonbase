package odyssey.backend.presentation.node;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.application.node.GenerateAiRoadmapUseCase;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.node.dto.response.AiRoadmapResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AiNodeController {

    private final GenerateAiRoadmapUseCase generateAiRoadmapUseCase;

    @PostMapping("/directories/{directoryId}/nodes/ai")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleCommonResponse<AiRoadmapResponse> createAiNodes(
            @PathVariable Long directoryId,
            @RequestParam(required = false) Long teamId,
            @RequestBody @Valid GenerateRoadmapRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(generateAiRoadmapUseCase.generateAiNodes(directoryId, teamId, request, user));
    }

}
