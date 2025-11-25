package odyssey.backend.application.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
import odyssey.backend.domain.text.Text;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.infrastructure.persistence.text.TextRepository;
import odyssey.backend.presentation.text.dto.request.TextRequest;
import odyssey.backend.presentation.text.dto.response.TextResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateTextUseCase {

    private final TextRepository textRepository;
    private final RoadmapRepository roadmapRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public TextResponse createText(TextRequest request, Long roadmapId) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(RoadmapNotFoundException::new);

        TextResponse response = TextResponse.from(
                textRepository.save(
                        Text.from(request, roadmap)
                )
        );

        if (roadmap.getTeam() != null) {
            messagingTemplate.convertAndSend("/topic/text/roadmap/" + roadmapId + "/created", response);
        }

        return response;
    }
}
