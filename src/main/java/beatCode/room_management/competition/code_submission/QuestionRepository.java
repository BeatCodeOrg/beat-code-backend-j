package beatCode.room_management.competition.code_submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findQuestionByQuestionId(int questionId);
}

