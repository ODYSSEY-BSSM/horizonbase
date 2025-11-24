package odyssey.backend.application.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.infrastructure.persistence.text.TextRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteTextUseCase {

    private final TextRepository textRepository;

    public void deleteText(Long textId) {
        textRepository.deleteById(textId);
    }

}
