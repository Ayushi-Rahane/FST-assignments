import java.rmi.Naming;
import java.util.Scanner;

public class QuizClient {
    public static void main(String[] args) {
        try {
            QuizInterface quiz = (QuizInterface) Naming.lookup("rmi://localhost/QuizService");
            Scanner sc = new Scanner(System.in);

            while (quiz.hasMoreQuestions()) {
                String question = quiz.getQuestion();
                System.out.println(question);

                System.out.print("Enter your answer: ");
                int answer = sc.nextInt();

                boolean result = quiz.checkAnswer(answer);

                if (result) {
                    System.out.println("Correct Answer\n");
                } else {
                    System.out.println("Wrong Answer\n");
                }
            }

            System.out.println("Final Score: " + quiz.getScore() + "/5");

        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}