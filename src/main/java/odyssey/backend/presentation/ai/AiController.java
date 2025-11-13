package odyssey.backend.presentation.ai;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.ai.AiService;
import odyssey.backend.presentation.ai.dto.request.GenerateRequest;
import odyssey.backend.presentation.ai.dto.response.GenerateResponse;
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
    public GenerateResponse generateAi(
            @RequestBody @Valid GenerateRequest generateRequest) {
        return aiService.generate(generateRequest);
    }

}
