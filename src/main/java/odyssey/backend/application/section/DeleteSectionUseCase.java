package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.section.Section;
import odyssey.backend.domain.section.exception.SectionNotFoundException;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteSectionUseCase {

    private final SectionRepository sectionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void deleteSection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(SectionNotFoundException::new);

        Long roadmapId = section.getRoadmap().getId();

        sectionRepository.deleteById(sectionId);

        messagingTemplate.convertAndSend("/topic/section/roadmap/" + roadmapId + "/deleted", sectionId);
    }

}
