package beatCode;

import org.springframework.stereotype.Service;

import user_authentication.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    private final Map<Integer, Integer> userScores; // Simulating user scores storage

    public ScoreService() {
        this.userScores = new HashMap<>();
    }

    public void updateScore(int userId, int score) {
        // Logic to update the score of a user in the scoreboard
        userScores.put(userId, score);
        System.out.println("Updated score for user " + userId + " with score: " + score);
        updateRank();
    }

    public void rankUsers() {
        // Logic to rank users based on their scores
        // For demonstration purposes, printing user scores and ranking
        System.out.println("Ranking users based on their scores:");
        userScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("User: " + entry.getKey() + ", Score: " + entry.getValue()));
    }

    private void updateRank() {
        // Recalculate ranks after a score update
        System.out.println("Updating ranks after score update...");
        // Sort users by scores
        userScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEachOrdered(entry -> {
                    // Update the rank for each user
                    // For demonstration purposes, you might want to update the rank in the database or elsewhere
                    // You can implement your own logic here based on the sorted user scores
                    System.out.println("Updating rank for user " + entry.getKey());
                });
    }

	public Map<Integer, Integer> getScores() {
		// TODO Auto-generated method stub
		return userScores;
	}

	public List<User> getRankings() {
		// TODO Auto-generated method stub
		return null;
	}
}
