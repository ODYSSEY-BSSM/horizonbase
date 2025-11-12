package odyssey.backend.node;

import odyssey.backend.domain.node.NodeType;
import odyssey.backend.domain.node.Subject;
import odyssey.backend.global.RestDocsSupport;
import odyssey.backend.presentation.node.dto.request.NodeRequest;
import odyssey.backend.presentation.node.dto.request.SubjectRequest;
import odyssey.backend.presentation.node.dto.response.NodeResponse;
import odyssey.backend.shared.color.Color;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NodeControllerTest extends RestDocsSupport {

    @WithMockUser
    @Test
    void 노드를_생성한다() throws Exception {
        Long roadmapId = 1L;
        NodeRequest request = new NodeRequest("노드 제목", "노드 설명", 100, 200,
                NodeType.TOP, 50, 60, Color.BLUE, null);

        NodeResponse response = new NodeResponse(1L, request.getTitle(), request.getDescription(),
                request.getHeight(), request.getWidth(), request.getType(), request.getX(), request.getY(), request.getColor(),
                roadmapId, null, null, 12, false, Subject.AI_GENERAL.getDescription());

        given(nodeService.createNode(eq(roadmapId), any(NodeRequest.class)))
                .willReturn(response);

        mvc.perform(post("/roadmap/{roadmapId}/nodes", roadmapId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("node-create",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("노드 제목"),
                                fieldWithPath("description").description("노드 설명"),
                                fieldWithPath("height").description("높이"),
                                fieldWithPath("width").description("너비"),
                                fieldWithPath("type").description("노드 타입"),
                                fieldWithPath("x").description("x 좌표"),
                                fieldWithPath("y").description("y 좌표"),
                                fieldWithPath("color").description("노드의 색"),
                                fieldWithPath("parentNodeId").optional().description("부모 노드 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.id").description("노드 ID"),
                                fieldWithPath("data.title").description("노드 제목"),
                                fieldWithPath("data.description").description("노드 설명"),
                                fieldWithPath("data.height").description("노드 높이"),
                                fieldWithPath("data.width").description("노드 너비"),
                                fieldWithPath("data.type").description("노드 타입"),
                                fieldWithPath("data.x").description("노드 X 좌표"),
                                fieldWithPath("data.y").description("노드 Y 좌표"),
                                fieldWithPath("data.color").description("노드의 색"),
                                fieldWithPath("data.roadmapId").description("로드맵 ID"),
                                fieldWithPath("data.parentNodeId").optional().description("부모 노드 ID"),
                                fieldWithPath("data.childNode").optional().description("자식 노드 목록"),
                                fieldWithPath(("data.progress")).optional().description("진행도(최하단 노드가 아닐 경우 null)"),
                                fieldWithPath("data.isEducation").optional().description("교육과정 노드인지 아닌지 구분"),
                                fieldWithPath("data.subject").optional().description("어떤 교과인지(enum)")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 부모가_있는_노드를_조회한다() throws Exception {
        Long roadmapId = 1L;
        Long nodeId = 2L;

        NodeResponse childNode = new NodeResponse(
                2L, "자식 노드 제목", "설명", 1, 2, NodeType.TOP, 50, 60, Color.BLUE, roadmapId, 1L, null,12, false, Subject.AI_GENERAL.getDescription()
        );

        given(nodeService.getNodeByIdAndRoadmapId(nodeId, roadmapId)).willReturn(childNode);

        mvc.perform(get("/roadmap/{roadmapId}/nodes/{nodeId}", roadmapId, nodeId))
                .andExpect(status().isOk())
                .andDo(document("node-get-with-parent",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID"),
                                parameterWithName("nodeId").description("노드 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.id").description("노드 ID"),
                                fieldWithPath("data.title").description("노드 제목"),
                                fieldWithPath("data.description").description("노드 설명"),
                                fieldWithPath("data.height").description("노드 높이"),
                                fieldWithPath("data.width").description("노드 너비"),
                                fieldWithPath("data.type").description("노드 타입"),
                                fieldWithPath("data.x").description("노드 X 좌표"),
                                fieldWithPath("data.y").description("노드 Y 좌표"),
                                fieldWithPath("data.color").description("노드의 색"),
                                fieldWithPath("data.roadmapId").description("로드맵 ID"),
                                fieldWithPath("data.parentNodeId").description("부모 노드 ID"),
                                fieldWithPath("data.childNode").optional().description("자식 노드 목록"),
                                fieldWithPath(("data.progress")).optional().description("진행도(최하단 노드가 아닐 경우 null)"),
                                fieldWithPath("data.isEducation").optional().description("교육과정 노드인지 아닌지 구분"),
                                fieldWithPath("data.subject").optional().description("어떤 교과인지(enum)")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 노드를_전체조회한다() throws Exception {
        Long roadmapId = 1L;

        NodeResponse parentNode = new NodeResponse(1L, "부모 노드", "부모 설명", 100, 200, NodeType.TOP, 50, 60, Color.BLUE, roadmapId, null, List.of(),12, false, Subject.AI_GENERAL.getDescription());
        NodeResponse childNode = new NodeResponse(2L, "자식 노드", "자식 설명", 110, 210, NodeType.TOP, 60, 70, Color.BLUE, roadmapId, 1L, null,12, false, Subject.AI_GENERAL.getDescription());

        given(nodeService.getNodesByRoadmapId(roadmapId)).willReturn(List.of(parentNode, childNode));

        mvc.perform(get("/roadmap/{roadmapId}/nodes", roadmapId))
                .andExpect(status().isOk())
                .andDo(document("node-get-all",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data[].id").description("노드 ID"),
                                fieldWithPath("data[].title").description("노드 제목"),
                                fieldWithPath("data[].description").description("노드 설명"),
                                fieldWithPath("data[].height").description("노드 높이"),
                                fieldWithPath("data[].width").description("노드 너비"),
                                fieldWithPath("data[].type").description("노드 타입"),
                                fieldWithPath("data[].x").description("노드 X 좌표"),
                                fieldWithPath("data[].y").description("노드 Y 좌표"),
                                fieldWithPath("data[].color").description("노드의 색"),
                                fieldWithPath("data[].roadmapId").description("로드맵 ID"),
                                fieldWithPath("data[].parentNodeId").optional().description("부모 노드 ID"),
                                fieldWithPath("data[].childNode").optional().description("자식 노드 목록"),
                                fieldWithPath(("data[].progress")).optional().description("진행도(최하단 노드가 아닐 경우 null)"),
                                fieldWithPath("data[].isEducation").optional().description("교육과정 노드인지 아닌지 구분"),
                                fieldWithPath("data[].subject").optional().description("어떤 교과인지(enum)")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 단일_노드를_조회한다() throws Exception {
        Long roadmapId = 1L;
        Long nodeId = 2L;

        NodeResponse node = new NodeResponse(2L, "자식 노드", "자식 설명", 110, 210, NodeType.TOP, 60, 70, Color.BLUE, roadmapId, 1L, null,12, false, Subject.AI_GENERAL.getDescription());

        given(nodeService.getNodeByIdAndRoadmapId(nodeId, roadmapId)).willReturn(node);

        mvc.perform(get("/roadmap/{roadmapId}/nodes/{nodeId}", roadmapId, nodeId))
                .andExpect(status().isOk())
                .andDo(document("node-get-one",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID"),
                                parameterWithName("nodeId").description("노드 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.id").description("노드 ID"),
                                fieldWithPath("data.title").description("노드 제목"),
                                fieldWithPath("data.description").description("노드 설명"),
                                fieldWithPath("data.height").description("노드 높이"),
                                fieldWithPath("data.width").description("노드 너비"),
                                fieldWithPath("data.type").description("노드 타입"),
                                fieldWithPath("data.x").description("노드 X 좌표"),
                                fieldWithPath("data.y").description("노드 Y 좌표"),
                                fieldWithPath("data.color").description("노드의 색"),
                                fieldWithPath("data.roadmapId").description("로드맵 ID"),
                                fieldWithPath("data.parentNodeId").description("부모 노드 ID"),
                                fieldWithPath("data.childNode").optional().description("자식 노드 목록"),
                                fieldWithPath(("data.progress")).optional().description("진행도(최하단 노드가 아닐 경우 null)"),
                                fieldWithPath("data.isEducation").optional().description("교육과정 노드인지 아닌지 구분"),
                                fieldWithPath("data.subject").optional().description("어떤 교과인지(enum)")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 노드를_수정한다() throws Exception {
        Long roadmapId = 1L;
        Long nodeId = 2L;

        NodeRequest request = new NodeRequest("수정된 노드", "수정 설명", 120, 220, NodeType.TOP, 70, 80, Color.BLUE, 1L);
        NodeResponse response = new NodeResponse(nodeId, request.getTitle(), request.getDescription(), request.getHeight(),
                request.getWidth(), request.getType(), request.getX(), request.getY(), request.getColor(),
                roadmapId, request.getParentNodeId(), null,12, false, Subject.AI_GENERAL.getDescription());

        given(nodeService.updateNode(eq(nodeId), eq(roadmapId), any(NodeRequest.class))).willReturn(response);

        mvc.perform(put("/roadmap/{roadmapId}/nodes/{nodeId}", roadmapId, nodeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("node-update",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID"),
                                parameterWithName("nodeId").description("노드 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("노드 제목"),
                                fieldWithPath("description").description("노드 설명"),
                                fieldWithPath("height").description("높이"),
                                fieldWithPath("width").description("너비"),
                                fieldWithPath("type").description("노드 타입"),
                                fieldWithPath("x").description("x 좌표"),
                                fieldWithPath("y").description("y 좌표"),
                                fieldWithPath("color").description("노드의 색"),
                                fieldWithPath("parentNodeId").description("부모 노드 ID").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.id").description("노드 ID"),
                                fieldWithPath("data.title").description("노드 제목"),
                                fieldWithPath("data.description").description("노드 설명"),
                                fieldWithPath("data.height").description("노드 높이"),
                                fieldWithPath("data.width").description("노드 너비"),
                                fieldWithPath("data.type").description("노드 타입"),
                                fieldWithPath("data.x").description("노드 X 좌표"),
                                fieldWithPath("data.y").description("노드 Y 좌표"),
                                fieldWithPath("data.color").description("노드의 색"),
                                fieldWithPath("data.roadmapId").description("로드맵 ID"),
                                fieldWithPath("data.parentNodeId").description("부모 노드 ID").optional(),
                                fieldWithPath("data.childNode").optional().description("자식 노드 목록"),
                                fieldWithPath("data.progress").optional().description("진행도(최하단 노드가 아닐 경우 null)"),
                                fieldWithPath("data.isEducation").optional().description("교육과정 노드인지 아닌지 구분"),
                                fieldWithPath("data.subject").optional().description("어떤 교과인지(enum)")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 노드를_삭제한다() throws Exception {
        Long roadmapId = 1L;
        Long nodeId = 2L;

        mvc.perform(delete("/roadmap/{roadmapId}/nodes/{nodeId}", roadmapId, nodeId))
                .andExpect(status().isOk())
                .andDo(document("node-delete",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID"),
                                parameterWithName("nodeId").description("노드 ID")
                        )
                ));
    }

    @WithMockUser
    @Test
    void 교육과정_노드로_전환한다() throws Exception {
        Long roadmapId = 1L;
        Long nodeId = 2L;

        SubjectRequest request = new SubjectRequest(Subject.AI_GENERAL);

        NodeResponse response = new NodeResponse(
                nodeId,
                "노드 제목",
                "노드 설명",
                100,
                200,
                NodeType.BOTTOM,
                50,
                60,
                Color.BLUE,
                roadmapId,
                null,
                null,
                0,
                true,
                Subject.AI_GENERAL.getDescription()
        );

        given(nodeService.changeEducation(eq(nodeId), eq(roadmapId), any(SubjectRequest.class)))
                .willReturn(response);

        mvc.perform(patch("/roadmap/{roadmapId}/nodes/{nodeId}", roadmapId, nodeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("node-change-education",
                        pathParameters(
                                parameterWithName("roadmapId").description("로드맵 ID"),
                                parameterWithName("nodeId").description("노드 ID")
                        ),
                        requestFields(
                                fieldWithPath("subject").description("전환할 교과명(enum)")
                        ),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data.id").description("노드 ID"),
                                fieldWithPath("data.title").description("노드 제목"),
                                fieldWithPath("data.description").description("노드 설명"),
                                fieldWithPath("data.height").description("노드 높이"),
                                fieldWithPath("data.width").description("노드 너비"),
                                fieldWithPath("data.type").description("노드 타입"),
                                fieldWithPath("data.x").description("노드 X 좌표"),
                                fieldWithPath("data.y").description("노드 Y 좌표"),
                                fieldWithPath("data.color").description("노드의 색"),
                                fieldWithPath("data.roadmapId").description("로드맵 ID"),
                                fieldWithPath("data.parentNodeId").optional().description("부모 노드 ID"),
                                fieldWithPath("data.childNode").optional().description("자식 노드 목록"),
                                fieldWithPath("data.progress").optional().description("진행도"),
                                fieldWithPath("data.isEducation").description("교육과정 노드 여부"),
                                fieldWithPath("data.subject").description("교과명(enum)")
                        )
                ));
    }

}
