package beatCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private static QuestionRepository questionRepository; // Assuming you have a repository for questions

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public static Question generateQuestion(String difficulty, String type) {
        // Implement logic to fetch a random question from the database
        // based on the specified difficulty and type
        // This logic will depend on your database structure and querying approach
        // Below is a simplified example assuming you have a repository method for fetching by difficulty and type

        // Use your repository method to get a random question based on the difficulty and type
        // Replace findByDifficultyAndType with your actual repository method name
        return questionRepository.findByDifficultyAndType(difficulty, type)
                .stream()
                .findAny() // Get any random question that matches
                .orElse(null);
    }
}
