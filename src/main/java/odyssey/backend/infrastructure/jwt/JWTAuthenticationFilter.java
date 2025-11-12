package odyssey.backend.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.auth.exception.UserNotFoundException;
import odyssey.backend.infrastructure.jwt.exception.InvalidTokenTypeException;
import odyssey.backend.infrastructure.jwt.exception.TokenExpiredException;
import odyssey.backend.infrastructure.jwt.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null) {
            try {
                Claims claims = tokenService.parseToken(token);

                String type = claims.get("type", String.class);
                if (!"ACCESS_TOKEN".equals(type)) {
                    throw new InvalidTokenTypeException();
                }

                Long uuid = claims.get("uuid", Long.class);
                String roleString = claims.get("role", String.class);

                User user = tokenService.getUserByUuid(uuid);

                if (user == null) {
                    throw new UserNotFoundException();
                }

                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleString));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                throw new TokenExpiredException();
            } catch (Exception e) {
                logger.error("JWT parsing error", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
