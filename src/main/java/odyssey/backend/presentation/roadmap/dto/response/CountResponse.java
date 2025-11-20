package odyssey.backend.presentation.roadmap.dto.response;

public record CountResponse(Long count) {
    public static CountResponse from(Long count) {
        return new CountResponse(count);
    }
}
