package odyssey.backend.presentation.education;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.EducationService;
import odyssey.backend.domain.auth.User;
import odyssey.backend.domain.node.Subject;
import odyssey.backend.presentation.education.dto.response.EducationResponse;
import odyssey.backend.presentation.roadmap.dto.response.CountResponse;
import odyssey.backend.presentation.roadmap.dto.response.SecondSimpleRoadmapResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.ListCommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus
@RequestMapping("/education")
public class EducationContorller {

    private final EducationService educationService;

    @GetMapping("/count")
    public SingleCommonResponse<CountResponse> countEducationNode(
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(educationService.getEducationNodeCount(user));
    }

    @GetMapping("/subject")
    public SingleCommonResponse<EducationResponse> getEducationInfo(
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(educationService.getSubjectList(user));
    }

    @GetMapping
    public ListCommonResponse<SecondSimpleRoadmapResponse> getEducationList(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "subject") Subject subject
    ){
        return CommonResponse.ok(educationService.getRoadmapBySubject(user, subject));
    }

}
