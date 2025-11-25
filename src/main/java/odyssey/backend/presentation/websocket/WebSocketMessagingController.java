package odyssey.backend.presentation.websocket;

import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.section.dto.request.UpdateSectionRequest;
import odyssey.backend.presentation.text.dto.request.UpdateTextRequest;
import odyssey.backend.presentation.websocket.dto.RealtimeUpdateMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebSocketMessagingController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/node/{nodeId}/roadmap/{roadmapId}/updated")
    public void broadcastNodeUpdate(
            @DestinationVariable Long nodeId,
            @DestinationVariable Long roadmapId,
            @Payload NodeRequest nodeRequest,
            Principal principal
    ) {
        User user = extractUser(principal);

        RealtimeUpdateMessage<NodeRequest> updateMessage = RealtimeUpdateMessage.of(nodeId, nodeRequest);

        messagingTemplate.convertAndSend(
                "/topic/node/roadmap/" + roadmapId + "/updated",
                updateMessage
        );
    }

    @MessageMapping("/section/{sectionId}/roadmap/{roadmapId}/updated")
    public void broadcaseSectionUpdate(
            @DestinationVariable Long sectionId,
            @DestinationVariable Long roadmapId,
            @Payload UpdateSectionRequest request,
            Principal principal
            ) {
        User user = extractUser(principal);

        RealtimeUpdateMessage<UpdateSectionRequest> updateMessage = RealtimeUpdateMessage.of(sectionId, request);

        messagingTemplate.convertAndSend(
                "/topic/section/roadmap/" + roadmapId + "/updated", updateMessage
        );
    }

    @MessageMapping("/text/{textId}/roadmap/{roadmapId}/updated")
    public void broadcastTextUpdate(
            @DestinationVariable Long textId,
            @DestinationVariable Long roadmapId,
            @Payload UpdateTextRequest request,
            Principal principal
            ) {
        User user = extractUser(principal);

        RealtimeUpdateMessage<UpdateTextRequest> updateMessage = RealtimeUpdateMessage.of(textId, request);

        messagingTemplate.convertAndSend(
                "/topic/text/roadmap/" + roadmapId + "/updated", updateMessage
        );
    }

    private User extractUser(Principal principal) {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
        return (User) auth.getPrincipal();
    }
}