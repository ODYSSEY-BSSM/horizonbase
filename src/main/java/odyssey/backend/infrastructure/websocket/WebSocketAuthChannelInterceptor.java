package odyssey.backend.infrastructure.websocket;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.UserNotFoundException;
import odyssey.backend.infrastructure.jwt.exception.InvalidTokenException;
import odyssey.backend.infrastructure.jwt.exception.TokenNotFoundException;
import odyssey.backend.infrastructure.jwt.service.TokenService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 여러 방법으로 토큰 추출 시도
            String token = extractToken(accessor);

            if (token != null) {
                try {
                    Claims claims = tokenService.parseToken(token);
                    Long uuid = claims.get("uuid", Long.class);
                    User user = tokenService.getUserByUuid(uuid);

                    if (user == null) {
                        log.error("WebSocket 인증 실패: uuid '{}'에 해당하는 사용자 없음", uuid);
                        throw new UserNotFoundException();
                    }

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                            );

                    accessor.setUser(authentication);
                } catch (Exception e) {
                    log.error("WebSocket 토큰 파싱 실패: {}", e.getMessage());
                    throw new InvalidTokenException();
                }
            } else {
                log.error("WebSocket 연결 시 토큰을 찾을 수 없음");
                throw new TokenNotFoundException();
            }
        }

        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        // 1. Cookie 헤더에서 추출
        String cookieHeader = accessor.getFirstNativeHeader("Cookie");

        if (cookieHeader != null) {
            String token = extractJwtFromCookie(cookieHeader);
            if (token != null) {
                return token;
            }
        }

        // 2. Authorization 헤더에서 추출 (fallback)
        String authHeader = accessor.getFirstNativeHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private String extractJwtFromCookie(String cookieHeader) {
        if (cookieHeader == null) {
            return null;
        }

        String[] cookies = cookieHeader.split(";");
        for (String cookie : cookies) {
            cookie = cookie.trim();
            if (cookie.startsWith("accessToken=")) {
                return cookie.substring("accessToken=".length());
            }
        }
        return null;
    }
}