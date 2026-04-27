import java.rmi.Remote;
import java.rmi.RemoteException;

// 1. Remote Interface
// This interface defines the methods that the client can call on the server.
// It MUST extend java.rmi.Remote.
public interface SimpleRMI extends Remote {
    
    // Every remote method MUST throw java.rmi.RemoteException to handle potential network issues.
    String sayHello() throws RemoteException;
}
