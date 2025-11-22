package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.shared.color.Color;

public record PersonalRoadmapResponse(
        RoadmapResponseVO roadmapInfo,
        Long uuid,
        Color color,
        String icon,
        int progress
) {
    public static PersonalRoadmapResponse from(Roadmap roadmap, Long uuid) {
        return new PersonalRoadmapResponse(
                RoadmapResponseVO.from(roadmap),
                uuid,
                roadmap.getColor(),
                roadmap.getIconCode(),
                roadmap.getProgress()
        );
    }
}
