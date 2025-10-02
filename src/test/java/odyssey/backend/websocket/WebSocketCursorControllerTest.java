package odyssey.backend.websocket;

import odyssey.backend.domain.auth.Role;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.websocket.WebSocketCursorController;
import odyssey.backend.presentation.websocket.dto.CursorPositionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSocketCursorControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private WebSocketCursorController controller;

    private User testUser;
    private Principal testPrincipal;

    @BeforeEach
    void setUp() {
        testUser = createTestUser(1L, "testUser");
        testPrincipal = createMockPrincipal(testUser);
    }

    private User createTestUser(Long uuid, String username) {
        User user = new User("test@example.com", username, "password", Role.USER);
        try {
            var uuidField = user.getClass().getDeclaredField("uuid");
            uuidField.setAccessible(true);
            uuidField.set(user, uuid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private Principal createMockPrincipal(User user) {
        Principal principal = org.mockito.Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn(user.getUsername());
        return principal;
    }

    @Test
    void 커서_위치를_브로드캐스트한다() {
        // given
        Long roadmapId = 1L;
        CursorPositionDto inputCursor = CursorPositionDto.builder()
                .x(100.0)
                .y(200.0)
                .build();

        // when
        controller.broadcastCursor(roadmapId, inputCursor, testPrincipal);

        // then
        ArgumentCaptor<CursorPositionDto> cursorCaptor = ArgumentCaptor.forClass(CursorPositionDto.class);
        verify(messagingTemplate).convertAndSend(
                eq("/topic/roadmap/1/cursor"),
                cursorCaptor.capture()
        );

        CursorPositionDto sentCursor = cursorCaptor.getValue();
        assertThat(sentCursor.getUserId()).isEqualTo(testUser.getUuid());
        assertThat(sentCursor.getUserName()).isEqualTo(testUser.getUsername());
        assertThat(sentCursor.getX()).isEqualTo(100.0);
        assertThat(sentCursor.getY()).isEqualTo(200.0);
    }

    @Test
    void 커서_브로드캐스트시_사용자_정보가_자동으로_추가된다() {
        // given
        Long roadmapId = 5L;
        CursorPositionDto inputCursor = CursorPositionDto.builder()
                .x(50.0)
                .y(75.0)
                .build();

        // when
        controller.broadcastCursor(roadmapId, inputCursor, testPrincipal);

        // then
        ArgumentCaptor<CursorPositionDto> cursorCaptor = ArgumentCaptor.forClass(CursorPositionDto.class);
        verify(messagingTemplate).convertAndSend(
                eq("/topic/roadmap/5/cursor"),
                cursorCaptor.capture()
        );

        CursorPositionDto sentCursor = cursorCaptor.getValue();
        assertThat(sentCursor.getUserId()).isNotNull();
        assertThat(sentCursor.getUserName()).isNotNull();
        assertThat(sentCursor.getUserId()).isEqualTo(1L);
        assertThat(sentCursor.getUserName()).isEqualTo("testUser");
    }

    @Test
    void 다른_로드맵ID에_대해_올바른_토픽으로_전송한다() {
        // given
        Long roadmapId = 999L;
        CursorPositionDto inputCursor = CursorPositionDto.builder()
                .x(10.0)
                .y(20.0)
                .build();

        // when
        controller.broadcastCursor(roadmapId, inputCursor, testPrincipal);

        // then
        verify(messagingTemplate).convertAndSend(
                eq("/topic/roadmap/999/cursor"),
                org.mockito.ArgumentMatchers.any(CursorPositionDto.class)
        );
    }

    @Test
    void 여러_사용자의_커서를_구분하여_전송한다() {
        // given
        User user1 = createTestUser(1L, "user1");
        User user2 = createTestUser(2L, "user2");
        Principal principal1 = createMockPrincipal(user1);
        Principal principal2 = createMockPrincipal(user2);
        Long roadmapId = 1L;

        CursorPositionDto cursor1 = CursorPositionDto.builder()
                .x(100.0)
                .y(200.0)
                .build();

        CursorPositionDto cursor2 = CursorPositionDto.builder()
                .x(300.0)
                .y(400.0)
                .build();

        // when
        controller.broadcastCursor(roadmapId, cursor1, principal1);
        controller.broadcastCursor(roadmapId, cursor2, principal2);

        // then
        ArgumentCaptor<CursorPositionDto> cursorCaptor = ArgumentCaptor.forClass(CursorPositionDto.class);
        verify(messagingTemplate, org.mockito.Mockito.times(2)).convertAndSend(
                eq("/topic/roadmap/1/cursor"),
                cursorCaptor.capture()
        );

        var sentCursors = cursorCaptor.getAllValues();
        assertThat(sentCursors).hasSize(2);
        assertThat(sentCursors.get(0).getUserId()).isEqualTo(1L);
        assertThat(sentCursors.get(1).getUserId()).isEqualTo(2L);
    }
}