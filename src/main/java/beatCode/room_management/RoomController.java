package beatCode.room_management;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.messaging.handler.annotation.*;

import beatCode.room_management.response_objects.*;

import beatCode.room_management.competition.CodeSubmitPayload;
import beatCode.room_management.competition.GamePlayerInfo;

@RestController
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


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("rooms/create/{user}")
    public RoomRequestResponse createRoom(@PathVariable String user) {
        Room createdRoom = roomService.createRoom(user);

        if (createdRoom == null) 
            return new RoomRequestResponse("-1", "server-failure", "temp-val");

        return new RoomRequestResponse(createdRoom.getCode(), "success", "temp-val");
    }

    @GetMapping("rooms/{code}")
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

    @MessageMapping("connect/{roomCode}/{username}")
    @SendTo("/topic/room/{roomCode}")
    public SocketActionResponse connectToRoom(@DestinationVariable String roomCode, @DestinationVariable String username) {

        // fetch room details with the roomCode
        // gonna need some sorta map of like a roomCode to a room 
        // ill get the room from the map and then add the user to the room

        Room currRoom = roomService.findRoomByCode(roomCode);
        if (currRoom == null)
            return new SocketErrorResponse(username, username, "no-room");

        if (!currRoom.containsUser(username))
            currRoom.addUser(username);

        return new SocketConnectionResponse(username, "all", currRoom.getUsers());

    // from : String 
    // to : String
    // function : String
    // data :
        // broadcast a message back with an updated list of all the people in the current room
    }

    @MessageMapping("start-game/{roomCode}")
    @SendTo("/topic/room/{roomCode}/start-game")
    public SocketActionResponse startGame(@DestinationVariable String roomCode) {
        Room currRoom = roomService.findRoomByCode(roomCode);
        if (currRoom == null)
            return new SocketErrorResponse("server", "server", "no-room");

        currRoom.initGameState();
        return new SocketActionResponse("server", "all", "starting", "start-game");
    }


    @MessageMapping("submit-code/{roomCode}")
    @SendTo("/topic/room/{roomCode}/update-score")
    public GamePlayerInfo[] submitCode(@DestinationVariable String roomCode, @Payload CodeSubmitPayload payload) {
        Room currRoom = roomService.findRoomByCode(roomCode);
        if (currRoom == null)
            return null;


        currRoom.updateScores(players);
        return players;
    }

}
