package odyssey.backend.presentation.ai;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.presentation.ai.dto.request.GenerateQuizRequest;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.ai.dto.request.ModifyNodeRequest;
import odyssey.backend.presentation.ai.dto.response.AiNodeListResponse;
import odyssey.backend.presentation.ai.dto.response.ModifyNodeResponse;
import odyssey.backend.presentation.ai.dto.response.QuizResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/roadmaps")
    public SingleCommonResponse<AiNodeListResponse> generateAiRoadmap(
            @RequestBody @Valid GenerateRoadmapRequest request
    ) {
        return CommonResponse.ok(aiService.generateRoadmap(request));
    }

    @PatchMapping("/nodes")
    public SingleCommonResponse<ModifyNodeResponse> updateAiNodes(
            @RequestBody @Valid ModifyNodeRequest request
    ){
        return CommonResponse.ok(aiService.modifyNode(request));
    }

    @PostMapping("/quizs")
    public SingleCommonResponse<QuizResponse> generateAiQuiz(
            @RequestBody @Valid GenerateQuizRequest request
    ){
        return CommonResponse.ok(aiService.generateQuiz(request));
    }

}
