import java.rmi.*;
import java.rmi.server.*;

// 2. Remote Object Implementation
// This class implements the interface we created and provides the actual logic.
// It extends UnicastRemoteObject to automatically handle the complicated network communication.
public class SimpleRMIImpl extends UnicastRemoteObject implements SimpleRMI {

    // The constructor MUST throw RemoteException because the parent
    // UnicastRemoteObject constructor does.
    protected SimpleRMIImpl() throws RemoteException {
        super(); // Calls the UnicastRemoteObject constructor
    }

    // This is the actual code that will run on the server when the client calls
    // sayHello()
    @Override
    public String sayHello() throws RemoteException {
        return "Hello! I am a simple message from the RMI Server.";
    }
}
