package beatCode;

public class User {

    private int id; // id in the database. 
    private String username;
    private String password;
    private int score;
    // private boolean isHost;  // when user creates a room, save into session(ishost, true)
    // show the game property selection and run button only when ishost === "true"


    public User() {
        // Default constructor
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

 
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/*
	public Room getConnectedRoom() {
		return connectedRoom;
	}

	public void setConnectedRoom(Room connectedRoom) {
		this.connectedRoom = connectedRoom;
	}
	*/

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    public boolean isHost() {
        return isHost;
    }

    public void setAsHost() {
        this.isHost = true;
    }
    */

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}



}
