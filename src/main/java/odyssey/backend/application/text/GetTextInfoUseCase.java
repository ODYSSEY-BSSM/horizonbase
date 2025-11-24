package odyssey.backend.application.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.text.exception.TextNotFoundException;
import odyssey.backend.infrastructure.persistence.text.TextRepository;
import odyssey.backend.presentation.text.dto.response.TextResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTextInfoUseCase {

    private final TextRepository textRepository;

    public TextResponse getTextInfo(Long textId){
        return TextResponse.from(
                textRepository.findById(textId).orElseThrow(
                        TextNotFoundException::new
                )
        );
    }
}
