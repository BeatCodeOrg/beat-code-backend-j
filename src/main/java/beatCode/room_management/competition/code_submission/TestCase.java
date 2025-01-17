package beatCode.room_management.competition.code_submission;

import jakarta.persistence.*;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_case_id")
    private int testCaseId;

    @Column(name = "input")
    private String input;

    @Column(name = "output")
    private String output;

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
