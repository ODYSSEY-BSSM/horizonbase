package odyssey.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import odyssey.backend.domain.auth.Role;
import odyssey.backend.domain.auth.User;
import odyssey.backend.global.ControllerTest;
import odyssey.backend.global.RestDocsSupport;
import odyssey.backend.infrastructure.jwt.dto.response.TokenResponse;
import odyssey.backend.presentation.auth.dto.request.LoginRequest;
import odyssey.backend.presentation.auth.dto.response.SignUpResponse;
import odyssey.backend.presentation.user.dto.request.SignUpRequest;
import odyssey.backend.presentation.user.dto.request.UpdatePasswordRequest;
import odyssey.backend.presentation.user.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends RestDocsSupport {

    @Test
    void 회원가입을_한다() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("fakeEmail@gmail.com", "이건우", "1234");

        SignUpResponse fakeResponse = new SignUpResponse(1L, signUpRequest.getEmail(), signUpRequest.getUsername(), Role.USER);

        given(signUpService.signUp(any(SignUpRequest.class)))
                .willReturn(fakeResponse);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.uuid").value(1L))
                .andExpect(jsonPath("$.data.email").value("fakeEmail@gmail.com"))
                .andDo(document("user-signup",
                        requestFields(
                                fieldWithPath("email").description("회원가입 이메일"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.uuid").description("유저 ID"),
                                fieldWithPath("data.email").description("유저 이메일"),
                                fieldWithPath("data.username").description("유저 이름"),
                                fieldWithPath("data.role").description("권한")
                        )
                ));
    }

    @Test
    void 로그인을_하면_액세스_토큰이_발급된다() throws Exception {
        LoginRequest request = new LoginRequest("fake@Email.com", "fakePassword");
        TokenResponse fakeTokenResponse = TokenResponse.create("fakeAccessToken", "fakeRefreshToken");

        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        given(loginService.login(any(LoginRequest.class)))
                .willReturn(fakeTokenResponse);

        mvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("fakeAccessToken"))
                .andExpect(jsonPath("$.data.refreshToken").value("fakeRefreshToken"))
                .andDo(document("user-login",
                        requestFields(
                                fieldWithPath("email").description("로그인 이메일"),
                                fieldWithPath("password").description("로그인 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.accessToken").description("발급된 액세스 토큰"),
                                fieldWithPath("data.refreshToken").description("발급된 리프레시 토큰")
                        )
                ));
    }


    @Test
    void 리프레시_토큰으로_액세스_토큰을_재발급한다() throws Exception {
        String fakeRefreshToken = "리프레시토큰 헤더에 넣어주세요";
        TokenResponse fakeTokenResponse = TokenResponse.create("newAccessToken", fakeRefreshToken);

        given(refreshService.refreshToken(fakeRefreshToken))
                .willReturn(fakeTokenResponse);

        mvc.perform(put("/auth/token")
                        .header("Refresh-Token", fakeRefreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.data.refreshToken").value(fakeRefreshToken))
                .andDo(document("user-refresh",
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.accessToken").description("새로 발급된 액세스 토큰"),
                                fieldWithPath("data.refreshToken").description("기존 리프레시 토큰")
                        )
                ));
    }

    @Test
    void 로그아웃_한다() throws Exception {
        String fakeAccessToken = "Bearer 액세스토큰 넣어주세요";

        willDoNothing().given(logoutService).logout(any());

        mvc.perform(delete("/auth")
                        .header("Authorization", fakeAccessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("로그아웃되었습니다."))
                .andDo(document("user-logout",
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("로그아웃 메시지")
                        )
                ));
    }

    @Test
    void 유저_정보를_가져온다() throws Exception {
        UserResponse fakeResponse = new UserResponse(
                "gunwoo",
                "fakeEmail@gmail.com",
                Role.USER.name(),
                List.of("팀1", "팀2"),
                "",
                Boolean.TRUE
        );

        given(getUserInfoService.getUserInfo(any()))
                .willReturn(fakeResponse);

        mvc.perform(get("/users")
                        .header("Authorization", "Bearer fakeAccessToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("gunwoo"))
                .andExpect(jsonPath("$.data.email").value("fakeEmail@gmail.com"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.teams[0]").value("팀1"))
                .andDo(document("user-info",
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.username").description("사용자 이름"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.role").description("사용자 권한"),
                                fieldWithPath("data.teams").description("사용자가 속한 팀 목록"),
                                fieldWithPath("data.school").description("연동된 학교 이름"),
                                fieldWithPath("data.isConnectedSchool").description("연동 여부")
                        )
                ));
    }

    @Test
    void 학교_연동을_한다() throws Exception {
        User fakeUser = new User();
        UserResponse fakeResponse = new UserResponse(
                "gunwoo",
                "fakeEmail@gmail.com",
                Role.USER.name(),
                List.of("팀1", "팀2"),
                "부산소프트웨어마이스터고등학교",
                true
        );

        given(connectSchoolUseCase.ConnectSchool(any()))
                .willReturn(fakeResponse);

        mvc.perform(put("/users/school")
                        .header("Authorization", "Bearer fakeAccessToken")
                        .with(ControllerTest.authenticationPrincipal(fakeUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("gunwoo"))
                .andExpect(jsonPath("$.data.email").value("fakeEmail@gmail.com"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.teams[0]").value("팀1"))
                .andExpect(jsonPath("$.data.school").value("부산소프트웨어마이스터고등학교"))
                .andExpect(jsonPath("$.data.isConnectedSchool").value(true))
                .andDo(document("user-connect-school",
                        responseFields(
                                fieldWithPath("code").description("응답 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.username").description("사용자 이름"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.role").description("사용자 권한"),
                                fieldWithPath("data.teams").description("사용자가 속한 팀 목록"),
                                fieldWithPath("data.school").description("연동된 학교 이름"),
                                fieldWithPath("data.isConnectedSchool").description("학교 연동 여부")
                        )
                ));
    }

    @Test
    void 비밀번호를_변경한다() throws Exception {
        User fakeUser = new User();
        UpdatePasswordRequest request = new UpdatePasswordRequest("24.040@bssm.hs.kr", "newPassword123!");

        doNothing().when(updatePasswordUseCase).updatePassword(any(UpdatePasswordRequest.class));

        mvc.perform(put("/users")
                        .header("Authorization", "Bearer fakeAccessToken")
                        .with(ControllerTest.authenticationPrincipal(fakeUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(document("user-update-password",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("새로 변경할 비밀번호")
                        )
                ));
    }

}
