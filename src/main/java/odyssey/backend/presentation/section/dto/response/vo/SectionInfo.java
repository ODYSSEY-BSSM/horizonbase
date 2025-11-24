package odyssey.backend.presentation.section.dto.response.vo;

import odyssey.backend.domain.section.Section;
import odyssey.backend.presentation.node.dto.response.SimpleNodeResponse;

import java.util.Collections;
import java.util.List;

public record SectionInfo(
        Long id,
        String name,
        Integer x,
        Integer y,
        Integer width,
        Integer height,
        List<SimpleNodeResponse> nodes
) {
    public static SectionInfo from(Section section){
        List<SimpleNodeResponse> nodes = section.getNodes() != null ?
                section.getNodes()
                        .stream()
                        .map(SimpleNodeResponse::from)
                        .toList()
                : Collections.emptyList();

        return new SectionInfo(
                section.getId(),
                section.getName(),
                section.getX(),
                section.getY(),
                section.getWidth(),
                section.getHeight(),
                nodes
        );
    }
}
