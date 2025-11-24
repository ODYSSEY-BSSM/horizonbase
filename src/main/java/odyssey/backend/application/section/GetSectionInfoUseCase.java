package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.section.Section;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import odyssey.backend.presentation.section.dto.response.SimpleSectionResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSectionInfoUseCase {

    private final SectionRepository sectionRepository;

    public SimpleSectionResponse getSectionInfo(Long sectionId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section not found."));

        return SimpleSectionResponse.from(section);
    }

}
