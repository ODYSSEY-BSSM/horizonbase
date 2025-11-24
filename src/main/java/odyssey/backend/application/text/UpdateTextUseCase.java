package odyssey.backend.application.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.section.Section;
import odyssey.backend.domain.section.exception.SectionNotFoundException;
import odyssey.backend.domain.text.Text;
import odyssey.backend.domain.text.exception.TextNotFoundException;
import odyssey.backend.infrastructure.persistence.section.SectionRepository;
import odyssey.backend.infrastructure.persistence.text.TextRepository;
import odyssey.backend.presentation.text.dto.request.UpdateTextRequest;
import odyssey.backend.presentation.text.dto.response.TextResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateTextUseCase {

    private final TextRepository textRepository;
    private final SectionRepository sectionRepository;

    public TextResponse updateText(UpdateTextRequest request, Long textId) {
        Text text = textRepository.findById(textId)
                .orElseThrow(TextNotFoundException::new);

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(SectionNotFoundException::new);

        text.update(request, section);

        return TextResponse.from(text);
    }
}
