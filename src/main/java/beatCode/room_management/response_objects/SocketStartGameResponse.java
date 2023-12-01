package beatCode.room_management.response_objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SocketStartGameResponse extends SocketActionResponse {
    private int questionId;
    private String questionText;

    public SocketStartGameResponse(String from, String to, int questionId, String questionText) {
        super(from, to, "success", "start-game");
        this.questionId = questionId;
        this.questionText = questionText;
    }

}
