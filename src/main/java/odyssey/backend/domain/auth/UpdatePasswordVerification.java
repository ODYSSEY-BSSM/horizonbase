package odyssey.backend.domain.auth;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import odyssey.backend.domain.auth.exception.InvalidVerificationCodeException;
import odyssey.backend.domain.auth.exception.NotVerificationUserException;
import org.springframework.data.redis.core.RedisHash;

import java.util.Random;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "update-password-verification", timeToLive = 60 * 10)
public class UpdatePasswordVerification {

    @Id
    private String email;

    private String code;

    private boolean isVerified;

    public UpdatePasswordVerification(String email){
        this.email = email;
        this.code = generateCode();
        this.isVerified = false;
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void verify(){
        isVerified = true;
    }


    public void validateCode(String requestCode){
        if(!code.equals(requestCode)){
            throw new InvalidVerificationCodeException();
        }
    }

    public void validateVerified(){
        if(!isVerified){
            throw new NotVerificationUserException();
        }
    }

}
