package odyssey.backend.presentation.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odyssey.backend.application.team.TeamService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.infrastructure.websocket.WebSocketSessionManager;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketSubscriptionController {

    private final WebSocketSessionManager sessionManager;
    private final TeamService teamService;



    @MessageMapping("/subscribe/team/{teamId}")
    public void subscribeToTeam(@DestinationVariable Long teamId,
                                Principal principal) {
        User user = extractUser(principal);
        if (!teamService.isUserMemberOfTeam(user.getUuid(), teamId)) {
            log.warn("팀 구독 권한 없음 - 사용자ID: {}, 팀ID: {}", user.getUuid(), teamId);
            throw new AccessDeniedException("해당 팀의 멤버가 아닙니다: " + teamId);
        }

        sessionManager.subscribeToTeam(user.getUuid(), teamId);
    }

    @MessageMapping("/unsubscribe/team/{teamId}")
    public void unsubscribeFromTeam(@DestinationVariable Long teamId,
                                    Principal principal) {
        User user = extractUser(principal);
        sessionManager.unsubscribeFromTeam(user.getUuid(), teamId);
    }

    @MessageMapping("/subscribe/roadmap/{roadmapId}")
    public void subscribeToRoadmap(@DestinationVariable Long roadmapId,
                                   Principal principal) {
        User user = extractUser(principal);
        sessionManager.subscribeToRoadmap(user.getUuid(), roadmapId);
    }

    @MessageMapping("/unsubscribe/roadmap/{roadmapId}")
    public void unsubscribeFromRoadmap(@DestinationVariable Long roadmapId,
                                       Principal principal) {
        User user = extractUser(principal);
        sessionManager.unsubscribeFromRoadmap(user.getUuid(), roadmapId);
    }

    private User extractUser(Principal principal) {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
        return (User) auth.getPrincipal();
    }

}
