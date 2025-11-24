package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.section.Section;
import odyssey.backend.domain.section.exception.SectionNotFoundException;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import odyssey.backend.presentation.section.dto.response.SimpleSectionResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSectionInfoUseCase {

    private final SectionRepository sectionRepository;

    public SimpleSectionResponse getSectionInfo(Long sectionId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(SectionNotFoundException::new);

        return SimpleSectionResponse.from(section);
    }

}
