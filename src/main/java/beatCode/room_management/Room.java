package beatCode.room_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beatCode.room_management.competition.GamePlayerInfo;

public class Room {

    private static String[] playerColors = {"red", "orange", "blue", "green", "purple", "black", "white"};

	private ArrayList<String> users = new ArrayList<>(); // username in the database
	private String code; 
	private HashMap<String, GamePlayerInfo> gameState;


	public Room(String code) {
		this.code = code;
		// this.host = user;
	}

    public boolean containsUser(String user) {
        return users.contains(user);
    }

    public void initGameState() {
        gameState = new HashMap<String, GamePlayerInfo>();
        for (int i = 0; i < users.size(); i++)
            gameState.put(users.get(i), 
                new GamePlayerInfo(users.get(i), playerColors[i]));
    }

    public void updateScores(String user, int score) {
        gameState.get(user).updateTestsPassed(score);
    }

    public HashMap<String, GamePlayerInfo> getGameState() {
        return gameState;
    }


	public void addUser(String user) {
		users.add(user);

	}

	public void removeUser(String user) {
		users.remove(user);

	}

    public String[] getUsers() {
        return users.toArray(new String[users.size()]);
    }

	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

}
