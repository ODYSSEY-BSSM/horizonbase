package odyssey.backend.infrastructure.ai;

import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.ai.exception.FailedAiResponseException;
import odyssey.backend.presentation.ai.dto.request.GenerateQuizRequest;
import odyssey.backend.presentation.ai.dto.request.GenerateRoadmapRequest;
import odyssey.backend.presentation.ai.dto.request.ModifyNodeRequest;
import odyssey.backend.presentation.ai.dto.response.AiNodeListResponse;
import odyssey.backend.presentation.ai.dto.response.ModifyNodeResponse;
import odyssey.backend.presentation.ai.dto.response.QuizResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AiService {

    private final WebClient webClient;

    @Value("${ai.base-url}")
    private String baseUrl;

    public AiNodeListResponse generate(GenerateRoadmapRequest request){
        try {
            return webClient.post()
                    .uri(baseUrl + "/create-roadmap")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(AiNodeListResponse.class)
                    .block();
        }catch (Exception e){
            throw new FailedAiResponseException();
        }
    }

    public ModifyNodeResponse modifyNode(ModifyNodeRequest request){
        try {
            return webClient.post()
                    .uri(baseUrl + "/modify-roadmap")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ModifyNodeResponse.class)
                    .block();
        }catch (Exception e){
            throw new FailedAiResponseException();
        }
    }

    public QuizResponse generateQuiz(GenerateQuizRequest request){
        try {
            return webClient.post()
                    .uri(baseUrl + "/quiz")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(QuizResponse.class)
                    .block();
        }catch (Exception e){
            throw new FailedAiResponseException();
        }
    }

}
