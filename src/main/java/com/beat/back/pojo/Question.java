package com.beat.back.pojo;




public class Question {


    private Long id;

    private String title;
    private String description;
    private String difficulty;
    private String type;
    private String skeletonCode;

    // Constructors, getters, and setters

    // Constructor
    public Question() {
        // Default constructor required by JPA
    }

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkeletonCode() {
        return skeletonCode;
    }

    public void setSkeletonCode(String skeletonCode) {
        this.skeletonCode = skeletonCode;
    }
}
