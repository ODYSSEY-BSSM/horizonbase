package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

import java.util.List;

public record TeamRoadmapListResponse(
        List<TeamRoadmapInfo> roadmaps
) {
    public static TeamRoadmapListResponse from(List<Roadmap> roadmaps) {
        return new TeamRoadmapListResponse(
                roadmaps.stream()
                        .map(TeamRoadmapInfo::from)
                        .toList()
        );
    }
}
