package odyssey.backend.presentation.roadmap.dto.response;

import odyssey.backend.domain.roadmap.Roadmap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record RoadmapResponseVO(
        Long id,
        String title,
        String description,
        List<String> categories,
        LocalDate lastModifiedAt,
        LocalDateTime lastAccessedAt,
        boolean isFavorite
) {
    public static RoadmapResponseVO from(Roadmap roadmap) {
        return new RoadmapResponseVO(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getCategories(),
                roadmap.getLastModifiedAt(),
                roadmap.getLastAccessedAt(),
                roadmap.getIsFavorite()
        );
    }
}
