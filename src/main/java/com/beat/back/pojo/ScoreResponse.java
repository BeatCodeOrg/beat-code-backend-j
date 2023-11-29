package com.beat.back.pojo;

public class ScoreResponse {
	private String username;
	private String roomCode;
	private int updatedScore;

	// Default constructor (required for JSON serialization)
	public ScoreResponse() {
	}

	// Constructor with fields
	public ScoreResponse(String username, String roomCode, int updatedScore) {
		this.username = username;
		this.roomCode = roomCode;
		this.updatedScore = updatedScore;
	}

	// Getters and setters for fields
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public int getUpdatedScore() {
		return updatedScore;
	}

	public void setUpdatedScore(int updatedScore) {
		this.updatedScore = updatedScore;
	}
}
