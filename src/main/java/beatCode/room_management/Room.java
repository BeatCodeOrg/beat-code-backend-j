package beatCode.room_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {

	private List<String> users = new ArrayList<>(); // username in the database
	private String code; // romecode
	private HashMap<String, Integer> scoreMap;
	private ArrayList<String> ranking;
    private GamePlayerInfo[] gameState;

	// Constructor, getters, and setters for Room class

	public Room(String code) {
		this.code = code;
		// this.host = user;
	}

    public boolean containsUser(String user) {
        return users.contains(user);
    }

    public void initGameState() {
        gameState = new GamePlayerInfo[users.length];
        for (int i = 0; i < users.length; i++)
            gameState[i] = new GamePlayerInfo(users[i]);
    }

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public void setCode(String code) {
		this.code = code;
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

	public HashMap<String, Integer> getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(HashMap<String, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}

	public ArrayList<String> getRanking() {
		return ranking;
	}

	public void setRanking(ArrayList<String> ranking) {
		this.ranking = ranking;
	}

	
	
	// Other room functionalities like starting a game, managing chat, etc.
}