package beatCode.user_authentication;

// import javax.persistence.*;

// @Entity
public class User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id; // id in the database.  

    private String username;
    private String pw;
    private int score;
    // private boolean isHost;  // when user creates a room, save into session(ishost, true)
    // show the game property selection and run button only when ishost === "true"


    public User() {
        // Default constructor
    }

    public User(String username, String password) {
        this.username = username;
        this.pw = password;
    }

 
    public int getId() {
		return user_id;
	}

	public void setId(int id) {
		this.user_id = id;
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
        return pw;
    }

    public void setPassword(String password) {
        this.pw = password;
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
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + pw + '\'' +
                '}';
    }

    public boolean authenticate(String enteredPassword) {
        return this.pw.equals(enteredPassword);
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}



}
