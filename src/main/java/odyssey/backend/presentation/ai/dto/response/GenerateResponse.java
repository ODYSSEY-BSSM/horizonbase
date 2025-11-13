package odyssey.backend.presentation.ai.dto.response;

public record GenerateResponse(
        String result
) {
    public static GenerateResponse from(String result){
        return new GenerateResponse(result);
    }
}
