package beatCode.room_management.response_objects;

public class SocketErrorResponse extends SocketActionResponse {
    private String error;

    public SocketErrorResponse(String from, String to, String error) {
        super(from, to, "failure", "error");
        this.error = error;
    }
}
