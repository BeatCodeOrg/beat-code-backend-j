package beatCode.room_management;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.messaging.handler.annotation.*;

import beatCode.room_management.response_objects.*;

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

    @MessageMapping("/room/connect/{roomCode}/{username}/{userId}")
    @SendTo("/topic/room/{roomCode}")
    public SocketActionResponse connectToRoom(@DestinationVariable String roomCode, @DestinationVariable String username, @DestinationVariable String userId) {

        // fetch room details with the roomCode
        // gonna need some sorta map of like a roomCode to a room 
        // ill get the room from the map and then add the user to the room

        Room currRoom = roomService.findRoomByCode(roomCode);
        if (currRoom == null)
            return new SocketErrorResponse(username, username, "no-room");

        currRoom.addUser(username);

        return new SocketConnectionResponse(username, "all", currRoom.getUsers());

    // from : String 
    // to : String
    // function : String
    // data :
        // broadcast a message back with an updated list of all the people in the current room
    }
}
