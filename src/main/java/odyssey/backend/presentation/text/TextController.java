package odyssey.backend.presentation.text;

import lombok.RequiredArgsConstructor;
import odyssey.backend.application.text.CreateTextUseCase;
import odyssey.backend.application.text.DeleteTextUseCase;
import odyssey.backend.application.text.GetTextInfoUseCase;
import odyssey.backend.application.text.UpdateTextUseCase;
import odyssey.backend.presentation.text.dto.request.TextRequest;
import odyssey.backend.presentation.text.dto.request.UpdateTextRequest;
import odyssey.backend.presentation.text.dto.response.TextResponse;
import odyssey.backend.shared.response.CommonResponse;
import odyssey.backend.shared.response.SingleCommonResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{roadmap-id}/text")
public class TextController {

    private final CreateTextUseCase createTextUseCase;
    private final DeleteTextUseCase deleteTextUseCase;
    private final UpdateTextUseCase updateTextUseCase;
    private final GetTextInfoUseCase getTextInfoUseCase;

    @PostMapping
    public SingleCommonResponse<TextResponse> createText(
            @PathVariable Long roadmapId,
            @RequestBody TextRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(createTextUseCase.createText(request, roadmapId));
    }

    @GetMapping("/{id}")
    public SingleCommonResponse<TextResponse> getText(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(getTextInfoUseCase.getTextInfo(id));
    }

    @PatchMapping("/{id}")
    public SingleCommonResponse<TextResponse> updateText(
            @PathVariable Long id,
            @RequestBody UpdateTextRequest request,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.ok(updateTextUseCase.updateText(request, id));
    }

    @DeleteMapping("/{id}")
    public void deleteText(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        deleteTextUseCase.deleteText(id);
    }
}
