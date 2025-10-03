package odyssey.backend.presentation.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.websocket.dto.CursorPositionDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketCursorController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/roadmap/{roadmapId}/cursor")
    public void broadcastCursor(
            @DestinationVariable Long roadmapId,
            @Payload CursorPositionDto cursor,
            Principal principal
    ) {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
        User user = (User) auth.getPrincipal();

        CursorPositionDto response = CursorPositionDto.builder()
                .userId(user.getUuid())
                .userName(user.getUsername())
                .x(cursor.getX())
                .y(cursor.getY())
                .build();

        messagingTemplate.convertAndSend(
                "/topic/roadmap/" + roadmapId + "/cursor",
                response
        );
    }
}
