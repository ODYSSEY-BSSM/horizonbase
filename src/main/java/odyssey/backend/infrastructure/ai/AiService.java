package odyssey.backend.infrastructure.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AiService {

    private final WebClient webClient;

    @Value("${ai.base-url}")
    private String baseUrl;

    public AiNodeListResponse generateRoadmap(GenerateRoadmapRequest request){
        try {
            return webClient.post()
                    .uri(baseUrl + "/create-roadmap")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(AiNodeListResponse.class)
                    .block();
        }catch (Exception e){
            log.error("AI 처리 중 알 수 없는 오류 발생: {}", e.getMessage(), e);
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
            log.error("AI 처리 중 알 수 없는 오류 발생: {}", e.getMessage(), e);
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
            log.error("AI 처리 중 알 수 없는 오류 발생: {}", e.getMessage(), e);
            throw new FailedAiResponseException();
        }
    }

}
