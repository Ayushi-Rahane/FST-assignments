import java.net.*; // Import classes for networking (sockets)
import java.io.*; // Import classes for reading and writing data
import java.util.Scanner; // Import Scanner to get user input from the console

public class EchoClient { // Define the main client class
    public static void main(String[] args) { // Main method where execution begins
        String hostName = "localhost"; // Define the IP address or name of the server to connect to (localhost means this computer)
        int portNumber = 5005; // Define the port number of the server

        try { // Start a try block to handle potential errors
            Socket echoSocket = new Socket(hostName, portNumber); // Create a new socket to connect to the server
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); // Create an object to send data to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); // Create an object to read data from the server
            Scanner stdIn = new Scanner(System.in); // Create a scanner to read what the user types on the keyboard

            System.out.println("Connected to Echo Server. Type your messages below:"); // Print a welcome message

            String userInput; // Variable to hold the user's typed message
            while (true) { // Loop continuously to allow sending multiple messages
                System.out.print("You: "); // Print a prompt for the user
                userInput = stdIn.nextLine(); // Read the line typed by the user

                if ("exit".equalsIgnoreCase(userInput)) { // Check if the user typed 'exit' to quit
                    break; // Exit the loop if the user wants to quit
                } // End of if statement

                out.println(userInput); // Send the user's message to the server
                String serverResponse = in.readLine(); // Wait for and read the response back from the server
                System.out.println("Server: " + serverResponse); // Print the server's response to the console
            } // End of while loop

            stdIn.close(); // Close the keyboard scanner
            in.close(); // Close the input stream from the server
            out.close(); // Close the output stream to the server
            echoSocket.close(); // Close the network connection to the server
            System.out.println("Disconnected from server."); // Print a message indicating disconnection

        } catch (UnknownHostException e) { // Catch an error if the server address is wrong
            System.err.println("Don't know about host " + hostName); // Print an error message
            System.exit(1); // Exit the program with an error code
        } catch (IOException e) { // Catch an error if we can't connect or communicate
            System.err.println("Couldn't get I/O for the connection to " + hostName); // Print an error message
            System.exit(1); // Exit the program with an error code
        } // End of try-catch blocks
    } // End of main method
} // End of EchoClient class
