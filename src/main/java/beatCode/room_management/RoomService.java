package beatCode.room_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beatCode.room_management.competition.code_submission.QuestionRepository;
import beatCode.room_management.competition.code_submission.TestCaseRepository;
import beatCode.room_management.competition.code_submission.TestCaseToQuestionRepository;

import beatCode.room_management.competition.code_submission.Judge;
import beatCode.room_management.competition.code_submission.Question;

@Service
public class RoomService {
	// enable GameController to access rooms?
    public ArrayList<Room> rooms = new ArrayList<Room>();
    public ArrayList<String> takenRoomCodes = new ArrayList<String>();
  
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    TestCaseToQuestionRepository testCaseToQuestionRepository;

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
        String roomCode = UUID.randomUUID().toString().substring(0, 6);

        while (takenRoomCodes.contains(roomCode))
            roomCode = UUID.randomUUID().toString().substring(0, 6);

        takenRoomCodes.add(roomCode);

        return roomCode;
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


    public Question getQuestion(int questionId) {
        return questionRepository.findQuestionByQuestionId(questionId);
    }

    public int runCode(String sourceCode, int questionId) {
        return Judge.runCode(sourceCode, questionId, questionRepository, testCaseRepository, testCaseToQuestionRepository);
    }



}
