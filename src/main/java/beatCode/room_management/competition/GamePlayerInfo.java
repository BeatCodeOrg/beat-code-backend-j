package beatCode.room_management.competition;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GamePlayerInfo {

    @JsonProperty("username")
    private String username;

    @JsonProperty("color")
    private String color;

    @JsonProperty("tests_passed")
    private int testsPassed;
    
    public GamePlayerInfo(String username, String color){
        this.username = username;
        this.color = color;
        this.testsPassed = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    public void updateTestsPassed(int testsPassed) {
        this.testsPassed = testsPassed;
    }
}
