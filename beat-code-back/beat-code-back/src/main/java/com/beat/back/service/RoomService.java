package com.beat.back.service;

import com.beat.back.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {
	// enable GameController to access rooms?
    public static List<Room> rooms = new ArrayList<>();
  

    @Autowired
    public RoomService() {
 
    }
    
    public Room createRoom(String user) {
        Room newRoom = new Room(generateRoomCode());
        rooms.add(newRoom);
        //designateHost(user, room);
        joinRoom(user, newRoom);
        return newRoom; // users, hostid, code
    }
    

    public void joinRoom(String user, Room room) {
        room.addUser(user);
        // Additional logic if needed when a user joins a room
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    // don't know how to call in GameController
    public void endGame(String roomCode) {
        // Call GameService to handle ending the game in the room
        Room room = findRoomByode(roomCode);
        rooms.remove(room);
        // Additional logic if needed when a game ends in a room
    }
    

    private Room findRoomByode(String roomCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private String generateRoomCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

	
	// Method to find a Room object by room code
    public Room findRoomByCode(String roomCode) {
        for (Room room : rooms) {
            if (room.getCode().equals(roomCode)) {
                return room; // Return the Room object if found
            }
        }
        return null; // Return null if no Room object with the given code is found
    }
	/*
    public void designateHost(String user, Room room) {
        room.setHost(user); // Assuming getUserId() retrieves the user's unique identifier
       // user.setAsHost(); // Designate the user as the host
    }
    */

    /*
    public void handleAction(String roomID, Map<String, Object> payload) {
        String action = (String) payload.get("action");
        Room room = null;
        int userId = (int) payload.get("userId");
        int roomId = Integer.parseInt(roomID);
        User user = null;
        switch (action) {
        
            
            case "joinRoom":
                user = getUserByID(userId);
                String code = (String) payload.get("code");
                room = findRoomByCode(code);
                if (room != null) {
                    joinRoom(user, room);
                }
                break;
            case "sendChatMessage":
                // Call a method to handle chat message
                chatService.handleChatMessage(roomId, (String) payload.get("userId"), (String) payload.get("message"));
                break;
            case "startGame":
                // Call a method to handle game start
                String type = (String) payload.get("type");
                String difficulty = (String) payload.get("difficulty");
                Question question = QuestionService.generateQuestion(difficulty, type);
                room = findRoomById(roomId);
                // loop through all users in the room, start game
                gameService.startGame(userId, question);
                break;
            case "submitCode":
                // Extract necessary data from payload and delegate to GameService for code submission
                gameService.submitCode(roomId, userId, (String) payload.get("code"));
                break;
            case "getScore":
                // Extract necessary data from payload and delegate to GameService to get user's score
                int score = gameService.getScore(roomId, userId);
                // Handle the score - return it, send it to the frontend, etc.
                // Example: You can create a response and return it to the client
                Map<String, Object> response = new HashMap<>();
                response.put("userId", userId);
                response.put("score", score);
                // Return the response to the client or process it further
                break;
            case "endGame":
                // front end first call submit code for every user to update the ranking and sore in the backend
                endGame(roomId);
                break;
            default:
                // Handle unknown or unsupported actions
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
    */





}
