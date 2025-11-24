package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.node.Node;
import odyssey.backend.domain.section.Section;
import odyssey.backend.infrastructure.persistence.node.NodeRepository;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import odyssey.backend.presentation.section.dto.request.UpdateSectionRequest;
import odyssey.backend.presentation.section.dto.response.SectionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateSectionUseCase {

    private final SectionRepository sectionRepository;
    private final NodeRepository nodeRepository;

    @Transactional
    public SectionResponse updateSection(UpdateSectionRequest request, Long sectionId) {
        List<Node> nodes = nodeRepository.findAllById(request.getNodeIds());

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("응응"));

        section.addNode(nodes);

        return SectionResponse.from(section);
    }

}
