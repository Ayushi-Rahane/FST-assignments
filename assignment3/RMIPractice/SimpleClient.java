import java.rmi.Naming;

// 4. Client Program
// This program connects to the server and calls the remote method.
public class SimpleClient {
    public static void main(String[] args) {
        try {
            // Step 1: Look up the remote object using the exact same name we registered it with.
            // Naming.lookup returns a generic Remote object, so we must cast it to our specific interface (SimpleRMI).
            SimpleRMI remoteObj = (SimpleRMI) Naming.lookup("rmi://localhost/SimpleMessage");
            
            // Step 2: Call the method on the remote object just like a normal local method!
            // All the complicated network communication happens magically in the background.
            String response = remoteObj.sayHello();
            
            // Step 3: Print the result we got back from the server.
            System.out.println("Server responded with: " + response);
            
        } catch (Exception e) {
            System.out.println("Client Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
