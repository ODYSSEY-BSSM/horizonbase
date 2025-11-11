package odyssey.backend.presentation.csrf;

import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping
    public SingleCommonResponse<CsrfToken> getCsrfToken(CsrfToken token) {
        return CommonResponse.ok(token);
    }

}
