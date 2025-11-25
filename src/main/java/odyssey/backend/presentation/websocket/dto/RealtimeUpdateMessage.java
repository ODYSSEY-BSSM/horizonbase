package odyssey.backend.presentation.websocket.dto;

public record RealtimeUpdateMessage<T>(
        Long id,
        T data
) {
    public static <T> RealtimeUpdateMessage<T> of(Long id, T data) {
        return new RealtimeUpdateMessage<>(id, data);
    }
}