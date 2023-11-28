package com.beat.back.controler;

//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {
 
//	private final RoomService roomService;
//	private final UserService userService;
//	//  private final SimpMessagingTemplate messagingTemplate;
//
//	  @Autowired
//    public GameController(SimpMessagingTemplate messagingTemplate, UserService userService, RoomService roomService) {
//        this.messagingTemplate = messagingTemplate;
//        this.userService = userService;
//        this.roomService = roomService;
//    }
//
//
//	  @PostMapping("/submit-code")
//	    public ResponseEntity<ScoreResponse> submitCode(@RequestBody CodeSubmissionRequest request) {
//	        // Process the submitted code and calculate the score
//	        String sourceCode = request.getSourceCode();
//	        String username = request.getUsername();
//	        String roomCode = request.getRoomCode();
//
//	        // Calculate the score based on the source code (Hypothetical calculation)
//	        int score = calculateTotalScore(sourceCode);
//
//
//
//			// Update the user's score in the backend (Hypothetical update)
//	        userService.updateUserScore(username, roomCode, score);
//
//	        // Get the updated score for the user
//	        //int updatedScore = userService.getUserScore(username, roomCode);
//
//	        // Create a response containing the updated score
//	        ScoreResponse response = new ScoreResponse(username, roomCode, score);
//
//	        // Broadcast the updated score to the correct user in the correct room via WebSocket
//	        // You'll need to implement WebSocket communication to send the score update to the user in the room
//
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//	    }
//
//	  // called only when front end finishes looping through all user and run their code
//	  @PostMapping("/end-game")
//	    public ResponseEntity<Result> endGame(@RequestParam("roomCode") String code) {
//		  	Result result = makeResult(code);
//		     // Broadcast the 'Result' object to all users in the room via WebSocket
//	        messagingTemplate.convertAndSend("/topic/room/{roomCode}", result);
//
//	        return new ResponseEntity<>(result, HttpStatus.OK);
//	    }
//
//
//	  // return the result ranking, user->score mapping for a room with a room code
//	  // better if there is a result table in database, for each roomCode it maps to its ranking and user->score map
//	private Result makeResult(String code) {
//		Room room = roomService.findRoomByCode(code);
//		Result result = new Result(room.getRanking(), room.getScoreMap());
//		return result;
//	}
//
//	private int calculateTotalScore(String sourceCode) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}
