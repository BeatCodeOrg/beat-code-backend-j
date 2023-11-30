package beatCode.room_management.response_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocketConnectionResponse extends SocketActionResponse {

    @JsonProperty("users")
    private String[] users;

    public SocketConnectionResponse(String from, String to, String[] users) {
        super(from, to, "success", "new-connection");
        this.users = users;
    }
}
