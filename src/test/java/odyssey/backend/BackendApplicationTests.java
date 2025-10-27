package odyssey.backend;

import odyssey.backend.infrastructure.jwt.service.TokenService;
import odyssey.backend.infrastructure.mail.MailUtil;
import odyssey.backend.infrastructure.persistence.auth.SignUpVerificationRepository;
import odyssey.backend.infrastructure.persistence.auth.UpdatePasswordVerificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
class BackendApplicationTests {

    @MockBean
    private TokenService tokenService;

    @MockBean
    private SignUpVerificationRepository signUpVerificationRepository;

    @MockBean
    private UpdatePasswordVerificationRepository updatePasswordVerificationRepository;

    @MockBean
    private MailUtil mailUtil;

    @Test
    void contextLoads() {
    }

}
