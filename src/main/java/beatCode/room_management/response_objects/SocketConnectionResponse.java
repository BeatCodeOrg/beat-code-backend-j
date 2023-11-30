package beatCode.room_management.response_objects;

public class SocketConnectionResponse extends SocketActionResponse {
    private String[] users;

    public SocketConnectionResponse(String from, String to, String[] users) {
        super(from, to, "success", "new-connection");
        this.users = users;
    }
}
