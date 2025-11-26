package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

public record RoadmapInfo(
        Long id,
        String title,
        String descrption,
        Long directoryId
) {
    public static RoadmapInfo from(Roadmap roadmap) {
        return new RoadmapInfo(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getDirectory().getId()
        );
    }
}
