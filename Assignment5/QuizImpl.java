import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class QuizImpl extends UnicastRemoteObject implements QuizInterface {

    private String[] questions = {
            "1. What is the capital of India?\n1) Mumbai\n2) Delhi\n3) Pune\n4) Chennai",
            "2. Which language is used in Java RMI?\n1) Python\n2) Java\n3) C++\n4) JavaScript",
            "3. What does JVM stand for?\n1) Java Variable Machine\n2) Java Virtual Machine\n3) Joint Virtual Machine\n4) Java Verified Machine",
            "4. Which package is used for RMI?\n1) java.io\n2) java.util\n3) java.rmi\n4) java.net",
            "5. Which method is used to register object?\n1) bind()\n2) lookup()\n3) rebind()\n4) register()"
    };

    private int[] answers = { 2, 2, 2, 3, 3 };

    private int currentQuestion = 0;
    private int score = 0;

    protected QuizImpl() throws RemoteException {
        super();
    }

    public String getQuestion() throws RemoteException {
        if (currentQuestion < questions.length) {
            return questions[currentQuestion];
        } else {
            return null;
        }
    }

    public boolean checkAnswer(int answer) throws RemoteException {
        boolean isCorrect = false;

        if (answer == answers[currentQuestion]) {
            score++;
            isCorrect = true;
        }

        currentQuestion++;
        return isCorrect;
    }

    public int getScore() throws RemoteException {
        return score;
    }

    public boolean hasMoreQuestions() throws RemoteException {
        return currentQuestion < questions.length;
    }
}