package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.shared.color.Color;

public record TeamRoadmapResponse(
        RoadmapResponseVO roadmapInfo,
        Long uuid,
        Color color,
        String icon,
        Long teamId,
        String teamName,
        Integer progress
) {
    public static TeamRoadmapResponse from(Roadmap roadmap, Long uuid){
        return new TeamRoadmapResponse(
                RoadmapResponseVO.from(roadmap),
                uuid,
                roadmap.getColor(),
                roadmap.getIconCode(),
                roadmap.getTeamId(),
                roadmap.getTeam().getName(),
                roadmap.getProgress()
        );
    }
}
