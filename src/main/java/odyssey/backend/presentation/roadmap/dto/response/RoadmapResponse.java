package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Icon;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.presentation.roadmap.dto.response.vo.RoadmapInfo;
import odyssey.backend.shared.color.Color;

public record RoadmapResponse(
        RoadmapInfo roadmapInfo,
        Color color,
        Icon icon,
        Integer progress
) {
    public static RoadmapResponse from(Roadmap roadmap) {
        return new RoadmapResponse(
                RoadmapInfo.from(roadmap),
                roadmap.getColor(),
                roadmap.getIcon(),
                roadmap.getProgress()
        );
    }
}
