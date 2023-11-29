package beatCode.room_management;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

	
    private final RoomService roomService;
    
    private final SimpMessagingTemplate messagingTemplate;

    
    // not correct
    @Autowired
    public RoomController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }


    @GetMapping("/create/{user}")
    public RoomRequestResponse createRoom(@PathVariable String user) {
        Room createdRoom = roomService.createRoom(user);

        if (createdRoom == null) 
            return new RoomRequestResponse("-1", "server-failure", "temp-val");

        return new RoomRequestResponse(createdRoom.getCode(), "success", "temp-val");
    }

    @GetMapping("/{code}")
    public RoomRequestResponse getRoomByCode(@PathVariable String code) {
        // Fetch room details (roomService.findRoomByCode(code))
    	Room room = roomService.findRoomByCode(code);// Retrieve room details

        // Assuming 'room' contains the updated room details
        // Send a WebSocket message to a specific room topic
        // messagingTemplate.convertAndSend("/topic/room/" + code, room);

        if (room == null) 
            return new RoomRequestResponse("-1", "no-room", "temp-val");

        return new RoomRequestResponse(room.getCode(), "found-room", "temp-val");
    }

    // chat, game all go through here
    /*
    @PostMapping("/{roomId}/processAction")
    public ResponseEntity<String> processAction(
            @PathVariable String roomId,
            @RequestBody Map<String, Object> payload
    ) {
        roomService.handleAction(roomId, payload);
        return ResponseEntity.ok("Action processed successfully");
    }
    */
}
