package odyssey.backend.user;

import odyssey.backend.global.RestDocsSupport;
import odyssey.backend.presentation.user.dto.request.SendVerificationRequest;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordVerifyRequest;
import odyssey.backend.presentation.user.dto.request.VerifyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VerificationControllerTest extends RestDocsSupport {

    @Test
    void 인증_메일을_전송한다() throws Exception {
        SendVerificationRequest request = new SendVerificationRequest("fakeEmail@gmail.com");

        willDoNothing().given(sendVerificationCodeService).sendVerificationCode(request);

        mvc.perform(post("/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(document("send-verification-code",
                        requestFields(
                                fieldWithPath("email").description("인증을 받을 이메일")
                        )
                ));
    }

    @Test
    void 인증_코드를_검증한다() throws Exception {
        VerifyRequest request = new VerifyRequest("fakeEmail@gmail.com", "123456");

        willDoNothing().given(verificationValidUseCase).verify(request);

        mvc.perform(patch("/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(document("verify-code",
                        requestFields(
                                fieldWithPath("email").description("인증 이메일"),
                                fieldWithPath("code").description("전송받은 인증 코드")
                        )
                ));
    }

    @Test
    void 비밀번호_변경_인증_메일을_전송한다() throws Exception {
        SendVerificationRequest request = new SendVerificationRequest("fakeEmail@gmail.com");

        willDoNothing().given(sendUpdatePasswordUseCase).sendUpdatePasswordVerificationMail(request);

        mvc.perform(post("/verification/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(document("send-verification-code",
                        requestFields(
                                fieldWithPath("email").description("인증을 받을 이메일")
                        )
                ));
    }

    @Test
    void 비밀번호_변경_인증_코드를_검증한다() throws Exception {
        UpdatePasswordVerifyRequest request = new UpdatePasswordVerifyRequest("fakeEmail@gmail.com", "123456");

        willDoNothing().given(verificationValidUseCase).verify(request);

        mvc.perform(put("/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(document("verify-code",
                        requestFields(
                                fieldWithPath("email").description("인증 이메일"),
                                fieldWithPath("code").description("전송받은 인증 코드")
                        )
                ));
    }
}
