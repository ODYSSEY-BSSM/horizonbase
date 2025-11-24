package odyssey.backend.presentation.text.dto.response.vo;

import odyssey.backend.domain.text.Text;
import odyssey.backend.domain.text.Type;

public record TextInfo(
        Long id,
        String text,
        Type type,
        Long roadmapId,
        Long sectionId
) {
    public static TextInfo from(Text text){
        Long sectionId = text.getSection().getId() != null ? text.getSection().getId() : null;
        return new TextInfo(text.getId(), text.getText(), text.getType(), text.getRoadmapId(),  sectionId);
    }

}
