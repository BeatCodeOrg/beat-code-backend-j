package beatCode.user_authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create") 
    public AuthenticationResponse createUser(@RequestBody UserDTO user) {

        String username = user.getUsername();
        String password = user.getPassword();

        System.out.println(user.toString());
        return userService.createUser(username, password);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody UserDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();

        System.out.println("logging in user");
        // Authenticate user based on username and password
        // User authenticatedUser = userService.authenticateUser(username, password);

        return userService.loginUser(username, password);
    }

    @GetMapping("/{id}") 
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Implement other CRUD operations as needed
}
