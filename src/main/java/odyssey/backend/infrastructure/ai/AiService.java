package odyssey.backend.infrastructure.ai;

import lombok.RequiredArgsConstructor;
import odyssey.backend.presentation.ai.dto.request.GenerateRequest;
import odyssey.backend.presentation.ai.dto.response.GenerateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AiService {

    private final WebClient webClient;

    @Value("${ai.base-url}")
    private String baseUrl;

    public GenerateResponse generate(GenerateRequest request){
        return webClient.post()
                .uri(baseUrl + "/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateResponse.class)
                .block();
    }

}
