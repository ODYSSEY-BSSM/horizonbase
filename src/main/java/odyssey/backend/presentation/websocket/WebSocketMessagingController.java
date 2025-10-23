package odyssey.backend.presentation.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
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
public class WebSocketMessagingController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/roadmap/{roadmapId}/nodes/{nodeId}")
    public void broadcastNodeUpdate(
            @DestinationVariable Long roadmapId,
            @DestinationVariable Long nodeId,
            @Payload NodeRequest nodeRequest,
            Principal principal
    ) {
        User user = extractUser(principal);

        log.debug("노드 실시간 메시징 - 로드맵ID: {}, 노드ID: {}, 사용자: {}", roadmapId, nodeId, user.getUsername());

        messagingTemplate.convertAndSend(
                "/topic/roadmap/" + roadmapId + "/nodes/" + nodeId,
                nodeRequest
        );
    }

    private User extractUser(Principal principal) {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
        return (User) auth.getPrincipal();
    }
}