import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuizInterface extends Remote {
    String getQuestion() throws RemoteException;

    boolean checkAnswer(int answer) throws RemoteException;

    int getScore() throws RemoteException;

    boolean hasMoreQuestions() throws RemoteException;
}