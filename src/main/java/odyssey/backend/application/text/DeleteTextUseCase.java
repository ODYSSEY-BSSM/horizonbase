package odyssey.backend.application.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.text.Text;
import odyssey.backend.domain.text.exception.TextNotFoundException;
import odyssey.backend.infrastructure.persistence.text.TextRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteTextUseCase {

    private final TextRepository textRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void deleteText(Long textId) {
        Text text = textRepository.findById(textId)
                .orElseThrow(TextNotFoundException::new);

        Long roadmapId = text.getRoadmapId();

        textRepository.deleteById(textId);

        if (text.getRoadmap().getTeam() != null) {
            messagingTemplate.convertAndSend("/topic/text/roadmap/" + roadmapId + "/deleted", textId);
        }
    }

}
