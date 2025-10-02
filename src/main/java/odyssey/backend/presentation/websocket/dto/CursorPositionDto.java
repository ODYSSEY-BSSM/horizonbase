package odyssey.backend.presentation.websocket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CursorPositionDto {
    private Long userId;
    private String userName;
    private Double x;
    private Double y;
}
