package com.beat.back.controler;

import com.beat.back.service.RoomService;
import com.beat.back.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

//
//    private final RoomService roomService;
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//
//    // not correct
//    @Autowired
//    public RoomController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
//        this.messagingTemplate = messagingTemplate;
//        this.roomService = roomService;
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<Room> createRoom(@RequestParam("user") String user) {
//        Room createdRoom = roomService.createRoom(user);
//        if (createdRoom != null) {
//        	// front end get room code, show it on the screen, and also get list of usernames, show in the playerlist
//            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/{code}")
//    public ResponseEntity<Room> getRoomByCode(@PathVariable String code) {
//        // Fetch room details (roomService.findRoomByCode(code))
//    	Room room = roomService.findRoomByCode(code);// Retrieve room details
//
//        // Assuming 'room' contains the updated room details
//        // Send a WebSocket message to a specific room topic
//        messagingTemplate.convertAndSend("/topic/room/" + code, room);
//
//        return new ResponseEntity<>(room, HttpStatus.OK);
//    }
//
//    // chat, game all go through here
//    /*
//    @PostMapping("/{roomId}/processAction")
//    public ResponseEntity<String> processAction(
//            @PathVariable String roomId,
//            @RequestBody Map<String, Object> payload
//    ) {
//        roomService.handleAction(roomId, payload);
//        return ResponseEntity.ok("Action processed successfully");
//    }
//    */
}
