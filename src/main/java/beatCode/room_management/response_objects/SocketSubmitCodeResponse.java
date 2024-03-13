package beatCode.room_management.response_objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import beatCode.room_management.competition.GamePlayerInfo;
import java.util.HashMap;

public class SocketSubmitCodeResponse extends SocketActionResponse {
    @JsonProperty("gameState")
    private HashMap<String, GamePlayerInfo> gameState;

    @JsonProperty("totalTestCase")
    private int totalTestCases;

    public SocketSubmitCodeResponse(String from, String to, HashMap<String, GamePlayerInfo> gameState, int totalTestCases) {
        super(from, to, "success", "submit-code");
        this.gameState = gameState;
        this.totalTestCases = totalTestCases;
    }
}
