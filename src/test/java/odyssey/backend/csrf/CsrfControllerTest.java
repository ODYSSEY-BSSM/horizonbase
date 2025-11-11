    package odyssey.backend.csrf;


    import odyssey.backend.global.RestDocsSupport;
    import org.junit.jupiter.api.Test;
    import org.springframework.security.test.context.support.WithMockUser;

    import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
    import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
    import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
    import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    public class CsrfControllerTest extends RestDocsSupport {

        @WithMockUser
        @Test
        void CSRF_토큰을_발급받는다() throws Exception {
            mvc.perform(get("/csrf")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andDo(document("csrf-get",
                            responseFields(
                                    fieldWithPath("code").description("응답 코드"),
                                    fieldWithPath("message").description("응답 메시지"),
                                    fieldWithPath("data.headerName").description("CSRF 토큰을 전송할 헤더 이름"),
                                    fieldWithPath("data.token").description("발급된 CSRF 토큰 값"),
                                    fieldWithPath("data.parameterName").description("CSRF 파라미터 이름")
                            )
                    ));
        }
    }
