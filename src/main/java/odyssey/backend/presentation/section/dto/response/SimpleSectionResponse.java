package odyssey.backend.presentation.section.dto.response;

import odyssey.backend.domain.section.Section;

public record SimpleSectionResponse(
        Long id,
        String name
) {
    public static SimpleSectionResponse from(Section section) {
        return new SimpleSectionResponse(
                section.getId(),
                section.getName()
        );
    }

}
