package beatCode.user_authentication;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

	// send back the userID from database
	public AuthenticationResponse createUser(String username, String password) {
		User foundUser = userRepository.findUserByUsername(username);

        if (foundUser != null)
            return new AuthenticationResponse(-1, "null", "unauthorized");
        
        User newUser = new User(username, password);
        userRepository.save(newUser);

        return new AuthenticationResponse(newUser.getId(), newUser.getUsername(), "authorized");
	}

	public AuthenticationResponse loginUser(String username, String password) {
		User foundUser = userRepository.findUserByUsername(username);

        if (foundUser == null || !foundUser.authenticate(password))
            return new AuthenticationResponse(-1, "null", "unauthorized");

		return new AuthenticationResponse(foundUser.getId(), foundUser.getUsername(), "authorized");
	}

	public User getUserById(int id) {
		// TODO Auto-generated method stub
        return userRepository.findById(id).orElse(null);
	}

	public void updateUserScore(String username, String roomCode, int score) {
	}



	// Other user-related methods can be added here
}
