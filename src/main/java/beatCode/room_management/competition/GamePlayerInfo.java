package beatCode.room_management.competition;

public class GamePlayerInfo {

    private String username;
    private int testsPassed;
    
    public GamePlayerInfo(String username){
        this.username = username;
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
