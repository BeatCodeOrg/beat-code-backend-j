package user_authentication;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
	private List<User> users = new ArrayList<>();

	// send back the userID from database
	public User createUser(String username, String password) {
		User foundUser = findUserByUsername(username);
		if (foundUser == null) {
			User newUser = new User(username, password);
			// int id = JDBC.insertNewUser(username, password);
			newUser.setId(999); // use the generated id from database
			users.add(newUser);
			return newUser;
		}
		return null;
	}

	public User loginUser(String username, String password) {
		User foundUser = findUserByUsername(username);
		if (foundUser != null && foundUser.authenticate(password)) {
			return foundUser;
		}
		return null; // Authentication failed
	}

	public List<User> getAllUsers() {
		return users;
	}

	// user database
	public User findUserByUsername(String username) {
		return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
	}

	// can be JDBC search
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if (user.getId() == id) {
				return user; // Found the user with the specified ID
			}
		}
		return null;

	}

	// can be JDBC search
	public User authenticateUser(String username, String password) {
		for (User user : users) {
	        if (user.getUsername().equals(username)) {
	            // Username found, now check if the password matches
	            if (user.getPassword().equals(password)) {
	                // Password matches, return the authenticated user
	                return user;
	            } else {
	                // Password doesn't match, return null or handle accordingly
	                return null;
	            }
	        }
	    }
	    // Username not found, return null or handle accordingly
	    return null;
	}

	public void updateUserScore(String username, String roomCode, int score) {
		// TODO Auto-generated method stub
		User user = findUserByUsername(username);
		user.setScore(score);
	}



	// Other user-related methods can be added here
}
