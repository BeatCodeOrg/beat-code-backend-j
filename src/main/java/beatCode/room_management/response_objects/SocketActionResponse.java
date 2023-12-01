package beatCode.room_management.response_objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SocketActionResponse {

    @JsonProperty("from")
    private String from; // username of user

    @JsonProperty("to")
    private String to; // username of one user or all

    @JsonProperty("status")
    private String status; // status of request: success / failure
    
    @JsonProperty("action")
    private String action; // action to be called

    public SocketActionResponse(String from, String to, String status, String action) {
        this.from = from;
        this.to = to;
        this.action = action;
    }
}
