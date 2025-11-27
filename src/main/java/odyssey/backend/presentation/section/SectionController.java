package odyssey.backend.presentation.section;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.section.CreateSectionUseCase;
import odyssey.backend.application.section.DeleteSectionUseCase;
import odyssey.backend.application.section.GetSectionInfoUseCase;
import odyssey.backend.application.section.UpdateSectionUseCase;
import odyssey.backend.domain.auth.User;
import odyssey.backend.presentation.section.dto.request.SectionRequest;
import odyssey.backend.presentation.section.dto.request.UpdateSectionRequest;
import odyssey.backend.presentation.section.dto.response.SectionResponse;
import odyssey.backend.presentation.section.dto.response.SimpleSectionResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{roadmap-id}/section")
public class SectionController {

    private final CreateSectionUseCase createSectionUseCase;
    private final GetSectionInfoUseCase getSectionInfoUseCase;
    private final DeleteSectionUseCase deleteSectionUseCase;
    private final UpdateSectionUseCase updateSectionUseCase;

    @PostMapping
    public SingleCommonResponse<SectionResponse> createSection(
            @PathVariable(value = "roadmap-id") Long roadmapId,
            @RequestBody SectionRequest request,
            @AuthenticationPrincipal User user)
    {
        return CommonResponse.ok(createSectionUseCase.createSection(request, roadmapId));
    }

    @GetMapping("/{section-id}")
    public SingleCommonResponse<SimpleSectionResponse> getSection(
            @PathVariable(value = "section-id") Long sectionId,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(getSectionInfoUseCase.getSectionInfo(sectionId));
    }

    @PatchMapping("/{section-id}")
    public SingleCommonResponse<SectionResponse> updateSection(
            @PathVariable(value = "section-id") Long sectionId,
            @RequestBody UpdateSectionRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(updateSectionUseCase.updateSection(request, sectionId));
    }

    @DeleteMapping("/{section-id}")
    public void deleteSection(
            @PathVariable(value = "section-id") Long sectionId,
            @AuthenticationPrincipal User user
    ){
        deleteSectionUseCase.deleteSection(sectionId);
    }

}
