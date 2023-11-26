package beatCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/questions")
public class QuestionController {
	  private final SimpMessagingTemplate messagingTemplate;

	    public QuestionController(SimpMessagingTemplate messagingTemplate) {
	        this.messagingTemplate = messagingTemplate;
	    }
	
	    // when host clicks run with type and difficulty filled, this function is called
	    @PostMapping("/api/generateQuestion")
	    public ResponseEntity<Question> generateQuestion(@RequestBody QuestionGenerationRequest request) {
	        String difficulty = request.getDifficulty();
	        String type = request.getType();
	        String roomCode = request.getRoomCode();
	        // Generate question based on difficulty and type by searching in sql database
	        Question question = QuestionService.generateQuestion(difficulty, type);
	        
	        // send this question to everyone in the room
	        if (question != null) {
	            // Broadcast the generated question to a specific WebSocket topic (room code)
	            messagingTemplate.convertAndSend("/topic/room/" + roomCode , question);

	            return new ResponseEntity<>(question, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}
