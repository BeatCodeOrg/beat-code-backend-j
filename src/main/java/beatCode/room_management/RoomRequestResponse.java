package beatCode.room_management;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomRequestResponse {
    @JsonProperty("room_code")
    private String roomCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("socket_id")
    private String socketId;

    public RoomRequestResponse(String code, String message, String socketId) {
        this.roomCode = code;
        this.message = message;
    }
}
