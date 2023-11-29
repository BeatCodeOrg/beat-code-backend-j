package com.beat.back.tools;


// when host clicks run, send back a json object with those three strings
public class QuestionGenerationRequest {

	private String type;
	private String difficulty;
	private String roomCode;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	public String getDifficulty() {
		// TODO Auto-generated method stub
		return difficulty;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}


	public String getRoomCode() {
		return roomCode;
	}


	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

}
