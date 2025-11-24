package odyssey.backend.application.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteSectionUseCase {

    private final SectionRepository sectionRepository;

    public void deleteSection(Long sectionId) {
        sectionRepository.deleteById(sectionId);
    }

}
