package beatCode.room_management.response_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocketErrorResponse extends SocketActionResponse {
    @JsonProperty("error")
    private String error;

    public SocketErrorResponse(String from, String to, String error) {
        super(from, to, "failure", "error");
        this.error = error;
    }
}
