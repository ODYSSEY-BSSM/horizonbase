package odyssey.backend.presentation.text.dto.response;

import odyssey.backend.domain.text.Text;
import odyssey.backend.presentation.text.dto.response.vo.TextInfo;

public record TextResponse(
        TextInfo texts
) {
    public static TextResponse from(Text text) {
        return new TextResponse(
                TextInfo.from(text)
        );
    }
}
