import java.rmi.Naming;

public class QuizServer {
    public static void main(String[] args) {
        try {
            QuizImpl obj = new QuizImpl();
            Naming.rebind("QuizService", obj);
            System.out.println("Server is ready...");
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }
}