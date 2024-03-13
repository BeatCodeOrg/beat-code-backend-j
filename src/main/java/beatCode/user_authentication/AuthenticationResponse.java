package beatCode.user_authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("user_id")
    private int user_id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("message")
    private String message;

    public AuthenticationResponse(int user_id, String username, String message) {
        this.user_id = user_id;
        this.username = username;
        this.message = message;
    }
}
