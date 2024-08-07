import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int checkAnswers(List<String> answers) {
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getAnswer().equalsIgnoreCase(answers.get(i))) {
                correctCount++;
            }
        }
        return correctCount;
    }
}
