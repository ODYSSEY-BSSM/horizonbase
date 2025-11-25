package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.domain.roadmap.exception.RoadmapNotFoundException;
import odyssey.backend.domain.section.Section;
import odyssey.backend.infrastructure.persistence.roadmap.RoadmapRepository;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import odyssey.backend.presentation.section.dto.request.SectionRequest;
import odyssey.backend.presentation.section.dto.response.SectionResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateSectionUseCase {

    private final SectionRepository sectionRepository;
    private final RoadmapRepository roadmapRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public SectionResponse createSection(SectionRequest request, Long roadmapId){
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(RoadmapNotFoundException::new);

        SectionResponse response = SectionResponse.from(sectionRepository.save(Section.from(request, roadmap)));

        if (roadmap.getTeam() == null) {
            messagingTemplate.convertAndSend("/topic/section/roadmap/" + roadmapId + "/created", response);
        }

        return response;
    }

}
