package com.beat.back.pojo;

public class CodeSubmissionRequest {
	private String sourceCode;
	private String username;
	private String roomCode;

	// Default constructor (required for JSON deserialization)
	public CodeSubmissionRequest() {
	}

	// Constructor with fields
	public CodeSubmissionRequest(String sourceCode, String username, String roomCode) {
		this.sourceCode = sourceCode;
		this.username = username;
		this.roomCode = roomCode;
	}

	// Getters and setters for fields
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

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
}
