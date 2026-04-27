import java.net.*; // Import classes for networking (sockets)
import java.io.*; // Import classes for reading and writing data

public class EchoServer { // Define the main server class
    public static void main(String[] args) { // Main method where execution begins
        int portNumber = 5005; // Define the port number where the server will listen

        try { // Start a try block to handle potential errors
            ServerSocket serverSocket = new ServerSocket(portNumber); // Create a new server socket listening on the specified port
            System.out.println("Server started and listening on port " + portNumber); // Print a message indicating the server is running

            while (true) { // Loop continuously to accept multiple clients
                Socket clientSocket = serverSocket.accept(); // Wait for and accept a new client connection
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress()); // Print the IP address of the connected client

                ClientHandler clientHandler = new ClientHandler(clientSocket); // Create a new handler for this specific client
                Thread clientThread = new Thread(clientHandler); // Create a new thread to run the client handler
                clientThread.start(); // Start the thread to handle the client in the background
            } // End of the while loop
        } catch (IOException e) { // Catch any input/output errors
            System.out.println("Exception caught when trying to listen on port " + portNumber); // Print an error message
            e.printStackTrace(); // Print the detailed error trace
        } // End of try-catch block
    } // End of main method
} // End of EchoServer class

class ClientHandler implements Runnable { // Define a class to handle client requests that can run in a thread
    private Socket clientSocket; // Variable to store the client's socket connection

    public ClientHandler(Socket socket) { // Constructor to initialize the client socket
        this.clientSocket = socket; // Assign the passed socket to the class variable
    } // End of constructor

    @Override // Indicate that we are providing our own implementation of the run method
    public void run() { // The method that runs when the thread starts
        try { // Start a try block to handle potential errors during communication
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Create an object to send data to the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Create an object to read data from the client

            String inputLine; // Variable to hold the message received from the client
            while ((inputLine = in.readLine()) != null) { // Read messages continuously as long as the client sends them
                System.out.println("Received from client: " + inputLine); // Print the received message on the server console
                out.println("Echo: " + inputLine); // Send the same message back to the client (echo)
            } // End of while loop reading messages

            in.close(); // Close the input stream
            out.close(); // Close the output stream
            clientSocket.close(); // Close the connection to the client
            System.out.println("Client disconnected."); // Print a message indicating the client disconnected
        } catch (IOException e) { // Catch any input/output errors during communication
            System.out.println("Error handling client connection"); // Print an error message
            e.printStackTrace(); // Print the detailed error trace
        } // End of try-catch block
    } // End of run method
} // End of ClientHandler class
