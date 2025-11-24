package odyssey.backend.presentation.section.dto.response;

import odyssey.backend.domain.section.Section;
import odyssey.backend.presentation.section.dto.response.vo.SectionInfo;

public record SectionResponse(
        SectionInfo section
) {
    public static SectionResponse from(Section section){
        return new SectionResponse(
                SectionInfo.from(section)
        );
    }
}
