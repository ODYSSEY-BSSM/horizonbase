package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

public record SecondSimpleRoadmapResponse(
        Long id,
        String title,
        String description,
        Integer progress
) {
    public static SecondSimpleRoadmapResponse from(Roadmap roadmap) {
        return new SecondSimpleRoadmapResponse(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getProgress()
        );
    }
}
