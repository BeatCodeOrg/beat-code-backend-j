package beatCode.room_management.response_objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import beatCode.room_management.competition.GamePlayerInfo;

public class SocketStartGameResponse extends SocketActionResponse {
    @JsonProperty("question_id")
    private int questionId;

    @JsonProperty("starter_code")
    private String starterCode;

    @JsonProperty("game_state")
    private HashMap<String, GamePlayerInfo> gameState;

    public SocketStartGameResponse(String from, String to, int questionId, String starterCode, HashMap<String, GamePlayerInfo> gameState) {
        super(from, to, "success", "start-game");
        this.questionId = questionId;
        this.starterCode = starterCode;
        this.gameState = gameState;
    }

}
