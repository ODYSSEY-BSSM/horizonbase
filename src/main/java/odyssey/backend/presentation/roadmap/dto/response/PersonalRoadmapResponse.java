package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

public record PersonalRoadmapResponse(
        RoadmapResponseVO roadmapInfo,
        Long uuid,
        String color,
        String icon,
        int progress
) {
    public static PersonalRoadmapResponse from(Roadmap roadmap, Long uuid) {
        return new PersonalRoadmapResponse(
                RoadmapResponseVO.from(roadmap),
                uuid,
                roadmap.getColorCode(),
                roadmap.getIconCode(),
                roadmap.getProgress()
        );
    }
}
