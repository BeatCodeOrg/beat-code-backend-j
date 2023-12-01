package beatCode.room_management.competition;

public class CodeSubmitPayload {
    private String username;
    private String code;
    private int questionId;

    public CodeSubmitPayload(String username, String code, int questionId) {
        this.username = username;
        this.code = code;
        this.questionId = questionId;
    }

    public String getUsername() {
        return username;
    }

    public String getCode() {
        return code;
    }

    public int getQuestionId() {
        return questionId;
    }
}

