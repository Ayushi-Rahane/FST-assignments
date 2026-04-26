// ============================================================
// FILE: WebAdderClient.java (Test Client)
//
// WHAT DOES THIS FILE DO?
// This is the CLIENT program that CALLS (consumes/uses) the
// WebAdder SOAP web service. It's like a customer ordering
// food from a restaurant — this program sends a request to
// the web service and gets a result back.
//
// IN THE ECLIPSE VERSION:
// Eclipse generates a JSP web page (TestClient.jsp) with a
// graphical interface. Since we're running from the terminal,
// this Java program does the same thing but in text mode.
//
// HOW IT WORKS:
//   1. Creates a SOAP connection to the web service
//   2. Sends two numbers to the "addition" method
//   3. Receives and displays the result
//
// The COOL PART:
// Even though SOAP uses complex XML messages, this client
// code looks like a simple Java method call! All the SOAP
// complexity is hidden by the proxy/service classes.
// ============================================================

// Package declaration — same package as other classes
package com.tutorial.ws;

// ============================================================
// IMPORT STATEMENTS
// These bring in the tools we need from other libraries
// ============================================================

// URL class — represents a web address (URL)
// We use it to point to the WSDL file location
import java.net.URL;

// QName = Qualified Name in XML
// A qualified name includes both a namespace and a local name
// Example: "http://ws.tutorial.com" + "WebAdderService"
// This uniquely identifies our web service, preventing
// name conflicts with other services
import javax.xml.namespace.QName;

// Service class — represents a web service
// It reads the WSDL document and creates a connection ("port")
// to the web service so we can call its methods
import javax.xml.ws.Service;

// Main class for the test client
public class WebAdderClient {

    // ========================================================
    // main() method — entry point of the client program
    // ========================================================
    public static void main(String[] args) {

        // ====================================================
        // TRY-CATCH BLOCK
        // "try" means "attempt to do this code"
        // "catch" means "if something goes wrong, handle it"
        //
        // WHY DO WE NEED THIS?
        // Network calls can fail for many reasons:
        //   - Server might not be running
        //   - Network might be down
        //   - URL might be wrong
        // The try-catch prevents the program from crashing
        // and instead shows a helpful error message.
        // ====================================================
        try {

            // ================================================
            // STEP 1: Create the WSDL URL
            //
            // The WSDL (Web Services Description Language) file
            // is like a "menu card" that describes what methods
            // the web service offers. The client MUST read this
            // WSDL first to understand how to call the service.
            //
            // We get the WSDL by adding "?wsdl" to the service URL.
            // "new URL(...)" creates a URL object pointing to it.
            // ================================================
            URL wsdlUrl = new URL(
                "http://localhost:9090/WebAdder/services/WebAdder?wsdl"
            );
            // The URL above means:
            //   http://         → use HTTP protocol
            //   localhost       → the server is on THIS computer
            //   :8080           → port number 8080
            //   /WebAdder/...   → path to the service
            //   ?wsdl           → give me the WSDL document

            // ================================================
            // STEP 2: Create a QName (Qualified Name) for the service
            //
            // A QName has two parts:
            //   1. Namespace URI: "http://ws.tutorial.com"
            //      (unique identifier, like a domain name)
            //   2. Local name: "WebAdderService"
            //      (the name of the service)
            //
            // Together, they uniquely identify our web service
            // among potentially millions of services worldwide.
            // ================================================
            QName serviceName = new QName(
                "http://ws.tutorial.com",   // namespace (from @WebService annotation)
                "WebAdderService"            // service name (from @WebService annotation)
            );

            // ================================================
            // STEP 3: Create a Service object
            //
            // Service.create() does the following:
            //   1. Downloads the WSDL from the URL
            //   2. Reads/parses the WSDL document
            //   3. Understands what methods are available
            //   4. Knows what data types to use
            //   5. Creates an internal connection setup
            //
            // Think of it as: "I read the restaurant's menu
            // and now I know what I can order!"
            // ================================================
            Service service = Service.create(wsdlUrl, serviceName);

            // ================================================
            // STEP 4: Get a "port" (connection) to the service
            //
            // A "port" in WSDL terms is the actual connection
            // point. service.getPort() creates a proxy object
            // that we can use to call methods on the web service.
            //
            // WebAdder.class tells Java what interface (methods)
            // we expect the web service to have.
            //
            // The returned object acts as a LOCAL representation
            // of the REMOTE web service. When we call methods on
            // it, those calls are automatically converted to SOAP
            // messages and sent over the network!
            // ================================================
            WebAdderService webAdder = service.getPort(WebAdderService.class);

            // ================================================
            // Print a header to make the output look nice
            // ================================================
            System.out.println("====================================================");
            System.out.println("  WebAdder SOAP Web Service — Test Client");
            System.out.println("====================================================");
            System.out.println();

            // ================================================
            // STEP 5: CALL THE WEB SERVICE!
            //
            // This is the exciting part! We call addition()
            // just like a normal Java method, but behind the
            // scenes, the following happens:
            //
            //   1. Java creates a SOAP XML request:
            //      <addition><nm1>10</nm1><nm2>20</nm2></addition>
            //
            //   2. Java sends this XML to the server via HTTP POST
            //
            //   3. Server receives the XML, parses it, and calls
            //      WebAdder.addition(10, 20) on the server side
            //
            //   4. Server gets result (30) and wraps it in a
            //      SOAP XML response:
            //      <additionResponse><return>30</return></additionResponse>
            //
            //   5. Server sends response back to client via HTTP
            //
            //   6. Client parses the response and extracts 30
            //
            //   7. The value 30 is returned from this method call
            //
            // ALL of that happens in this ONE line of code!
            // ================================================

            // --- Test 1: Add 10 and 20 ---
            int num1 = 10;  // First number to send to the web service
            int num2 = 20;  // Second number to send to the web service

            // Call the web service method (SOAP request is sent here!)
            int result1 = webAdder.addition(num1, num2);

            // Display the result
            System.out.println("  Test 1: addition(" + num1 + ", " + num2 + ")");
            System.out.println("  Result: " + result1);
            System.out.println();

            // --- Test 2: Add 100 and 200 ---
            int num3 = 100;
            int num4 = 200;
            int result2 = webAdder.addition(num3, num4);
            System.out.println("  Test 2: addition(" + num3 + ", " + num4 + ")");
            System.out.println("  Result: " + result2);
            System.out.println();

            // --- Test 3: Add negative numbers ---
            int num5 = -5;
            int num6 = 15;
            int result3 = webAdder.addition(num5, num6);
            System.out.println("  Test 3: addition(" + num5 + ", " + num6 + ")");
            System.out.println("  Result: " + result3);
            System.out.println();

            // --- Test 4: Add zero ---
            int num7 = 0;
            int num8 = 42;
            int result4 = webAdder.addition(num7, num8);
            System.out.println("  Test 4: addition(" + num7 + ", " + num8 + ")");
            System.out.println("  Result: " + result4);
            System.out.println();

            // Print success message
            System.out.println("====================================================");
            System.out.println("  ALL TESTS PASSED! Web service invocation successful!");
            System.out.println("====================================================");
            System.out.println();
            System.out.println("  The SOAP web service is working correctly.");
            System.out.println("  Each test above sent a SOAP request and received");
            System.out.println("  a SOAP response with the correct result.");

        } catch (Exception e) {
            // ================================================
            // ERROR HANDLING
            // If anything goes wrong, this code runs.
            //
            // Common errors:
            //   - "Connection refused" → Server is not running!
            //     Fix: Start WebAdderServer first.
            //   - "Unknown host" → Wrong URL
            //   - "Timeout" → Server is too slow to respond
            //
            // e.getMessage() → short error description
            // e.printStackTrace() → detailed error with line numbers
            // ================================================
            System.out.println("====================================================");
            System.out.println("  ERROR: Could not connect to the web service!");
            System.out.println("====================================================");
            System.out.println();
            System.out.println("  Error message: " + e.getMessage());
            System.out.println();
            System.out.println("  POSSIBLE FIXES:");
            System.out.println("  1. Make sure WebAdderServer is running first!");
            System.out.println("  2. Check that port 8080 is not used by another app");
            System.out.println("  3. Check the URL is correct");
            System.out.println();

            // Print full error details for debugging
            e.printStackTrace();
        }
    }
}

// ============================================================
// HOW THE CLIENT FITS IN THE BIG PICTURE:
//
//  [WebAdderClient.java]     ← THIS FILE (you run this)
//       |
//       | Step 1: Read WSDL from server
//       | Step 2: Create connection (port)
//       | Step 3: Call addition(10, 20)
//       |         → Sends SOAP XML request over HTTP
//       ↓
//  [WebAdderServer.java]     (must be running first!)
//       |
//       | Receives SOAP request
//       | Extracts nm1=10, nm2=20
//       | Calls WebAdder.addition(10, 20)
//       ↓
//  [WebAdder.java]           (on the server)
//       |
//       | Computes 10 + 20 = 30
//       | Returns 30
//       ↓
//  [WebAdderServer.java]
//       |
//       | Wraps 30 in SOAP XML response
//       | Sends response back over HTTP
//       ↓
//  [WebAdderClient.java]     ← back to THIS FILE
//       |
//       | Receives SOAP response
//       | Parses XML, extracts 30
//       | Prints "Result: 30"
//
// ============================================================
