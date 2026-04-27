import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// 3. Server Program
// This program starts the server and registers the remote object so clients can find it.
public class SimpleServer {
    public static void main(String[] args) {
        try {
            // Step 1: Start the RMI registry directly from our code on port 1099 (the default RMI port).
            // Doing this means you don't have to manually type 'rmiregistry' in the terminal!
            LocateRegistry.createRegistry(1099);

            // Step 2: Create the actual object that has the logic.
            SimpleRMIImpl obj = new SimpleRMIImpl();
            
            // Step 3: Bind (register) our object to the RMI registry with a name ("SimpleMessage").
            // Clients will use this exact name to look up the object over the network.
            Naming.rebind("rmi://localhost/SimpleMessage", obj);
            
            System.out.println("Server is running and waiting for clients...");
            
        } catch (Exception e) {
            System.out.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
