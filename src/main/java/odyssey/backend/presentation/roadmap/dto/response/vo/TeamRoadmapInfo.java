package odyssey.backend.presentation.roadmap.dto.response.vo;

import odyssey.backend.domain.roadmap.Icon;
import odyssey.backend.domain.roadmap.Roadmap;
import odyssey.backend.shared.color.Color;

public record TeamRoadmapInfo(
        Long id,
        String title,
        String description,
        Long directoryId,
        Long teamId,
        String teamName,
        Color color,
        Icon icon,
        Integer progress
) {
    public static TeamRoadmapInfo from(Roadmap roadmap) {
        return new TeamRoadmapInfo(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getDirectoryId(),
                roadmap.getTeamId(),
                roadmap.getTeam().getName(),
                roadmap.getColor(),
                roadmap.getIcon(),
                roadmap.getProgress()
        );
    }
}
