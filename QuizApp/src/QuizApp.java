import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    private static Quiz quiz = new Quiz();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Quiz Application");
            System.out.println("1. Add a question");
            System.out.println("2. Take the quiz");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addQuestion();
                    break;
                case 2:
                    takeQuiz();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addQuestion() {
        System.out.print("Enter the question: ");
        String questionText = scanner.nextLine();
        System.out.print("Enter the answer: ");
        String answer = scanner.nextLine();
        Question question = new Question(questionText, answer);
        quiz.addQuestion(question);
        System.out.println("Question added successfully.");
    }

    private static void takeQuiz() {
        List<Question> questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        List<String> userAnswers = new ArrayList<>();
        for (Question question : questions) {
            System.out.println(question);
            System.out.print("Your answer: ");
            String answer = scanner.nextLine();
            userAnswers.add(answer);
        }

        int correctAnswers = quiz.checkAnswers(userAnswers);
        System.out.println("You got " + correctAnswers + " out of " + questions.size() + " questions correct.");
    }
}
