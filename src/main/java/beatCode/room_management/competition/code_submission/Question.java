package beatCode.room_management.competition.code_submission;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int questionId;
    
    @Column(name = "title")
    private String title;
    // private String description;
    // private String title_slug;
    // private int difficulty;
    // private String topic_slugs;

    @Column(name = "main_def")
    private String main_def;

    public Question() {
        this.questionId = 0;
        this.title = "";
        // this.description = "";
        // this.title_slug = "";
        // this.difficulty = 0;
        // this.topic_slugs = "";
        this.main_def = "";
    }

    public Question(int id, String title, String main_def) {
        this.questionId = id;
        this.title = title;
        this.main_def = main_def;
    }

    public int getId() { return questionId; }

    public String getTitle() { return title; }

    // public String getDescription() { return description; }

    // public int getDifficulty() { return difficulty; }

    // public String getType() { return type; }

    public String getSkeletonCode() { return main_def; }

}
