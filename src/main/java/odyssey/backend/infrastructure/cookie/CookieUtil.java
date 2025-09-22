package odyssey.backend.infrastructure.cookie;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;

import odyssey.backend.infrastructure.cookie.exception.FailedSaveCookieException;

import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        try {
            ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(maxAge)
                .httpOnly(false)
                .secure(true)
                .sameSite("None")
                .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }catch(Exception e){
            throw new FailedSaveCookieException();
        }
    }

}
