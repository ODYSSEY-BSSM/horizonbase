package odyssey.backend.presentation.ai;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.ai.dto.response.AiNodeListResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping
    public SingleCommonResponse<AiNodeListResponse> generateAiRoadmap(
            @RequestBody @Valid GenerateRoadmapRequest generateRequest) {
        return CommonResponse.ok(aiService.generate(generateRequest));
    }

}
