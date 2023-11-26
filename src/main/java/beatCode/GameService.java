package beatCode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final Judge judge;
    private final ScoreService scoreService;

    @Autowired
    public GameService(Judge judge, ScoreService scoreService) {
        this.judge = judge;
        this.scoreService = scoreService;
    }

    // mapping to front end
    public void startGame(int userId, Question question) {
        // send back the question as json array to the user

    }

    public List<Integer> submitCode(int roomId, int userId, String code) {
        List<Integer> scores = judge.evaluateCode(code, roomId, userId);
        updateScore(userId, scores);
        return scores;
    }

    public int calculateTotalScore(List<Integer> scores) {
        int totalScore = 0;
        for (int score : scores) {
            totalScore += score;
        }
        return totalScore;
    }

    private void updateScore(int userId, List<Integer> scores) {
        int totalScore = calculateTotalScore(scores);
        scoreService.updateScore(userId, totalScore);
    }




}
