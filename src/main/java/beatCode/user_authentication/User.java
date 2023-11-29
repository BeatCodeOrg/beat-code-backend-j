package beatCode.user_authentication;


import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id; // id in the database.  
    
    @Column(name = "username")
    private String username;

    @Column(name = "pw")
    private String pw;

    // private boolean isHost;  // when user creates a room, save into session(ishost, true)
    // show the game property selection and run button only when ishost === "true"


    public User(String username, String pw) {
        this.username = username;
        this.pw = pw;
    }

    public int getId() {
		return user_id;
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


    public String getPassword() {
        return pw;
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

	// public int getScore() {
	// 	return score;
	// }

	// public void setScore(int score) {
	// 	this.score = score;
	// }



}
