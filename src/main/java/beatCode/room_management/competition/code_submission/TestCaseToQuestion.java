package beatCode.room_management.competition.code_submission;

import jakarta.persistence.*;

@Entity
@Table(name = "question_test_cases")
public class TestCaseToQuestion { 

    @Id
    @Column(name = "test_case_id")
    // @OneToOne(mappedBy="test_cases")
    private int id;

    @Column(name = "question_id")
    // @ManyToOne(mappedBy="questions")
    private int questionId;

    public int getQuestionId() {
        return questionId;
    }

    public int getTestCaseId() {
        return id;
    }

}
