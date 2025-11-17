package odyssey.backend.infrastructure.websocket;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = extractToken(accessor);

            if (token != null) {
                try {
                    Claims claims = tokenService.parseToken(token);
                    Long uuid = claims.get("uuid", Long.class);
                    User user = tokenService.getUserByUuid(uuid);

                    if (user == null) {
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
                    throw new InvalidTokenException();
                }
            } else {
                throw new TokenNotFoundException();
            }
        }

        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        String cookieHeader = accessor.getFirstNativeHeader("Cookie");

        if (cookieHeader != null) {
            String token = extractJwtFromCookie(cookieHeader);
            if (token != null) {
                return token;
            }
        }

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