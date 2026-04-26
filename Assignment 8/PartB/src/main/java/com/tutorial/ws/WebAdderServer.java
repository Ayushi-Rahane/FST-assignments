// ============================================================
// FILE: WebAdderServer.java (Server Launcher)
//
// WHAT DOES THIS FILE DO?
// This file STARTS the SOAP web service server.
// When you run this program, it:
//   1. Creates an instance of the WebAdder class
//   2. Publishes it as a SOAP web service at a specific URL
//   3. The service then waits for SOAP requests from clients
//
// IN THE ECLIPSE VERSION:
// You don't need this file because Tomcat server does this job.
// Tomcat reads the web.xml and WSDL files to publish the service.
// But since we're running WITHOUT Eclipse and Tomcat, we use
// Java's built-in Endpoint.publish() method to start a small
// HTTP server that serves our web service.
//
// HOW TO RUN:
// This is the first program you run. Start the server, then
// run the client to test it.
// ============================================================

// Package declaration — same package as WebAdder
package com.tutorial.ws;

// ============================================================
// IMPORT STATEMENT
// ============================================================

// Endpoint is a class from JAX-WS library that can publish
// (start serving) a web service at a given URL.
// Think of it like opening a shop at a specific address —
// Endpoint.publish() opens the shop and starts serving customers.
import javax.xml.ws.Endpoint;

// Main class that starts the web service server
public class WebAdderServer {

    // ========================================================
    // main() method — the entry point of the program.
    // When you run "java WebAdderServer", Java looks for this
    // method and starts executing from here.
    // "public" = accessible from anywhere
    // "static" = can be called without creating an object
    // "void"   = doesn't return any value
    // "String[] args" = command-line arguments (not used here)
    // ========================================================
    public static void main(String[] args) {

        // ====================================================
        // STEP 1: Define the URL where the service will run
        // This is the "address" where our web service will be
        // available. Clients will send SOAP requests to this URL.
        //
        // "http://" = protocol (how to communicate)
        // "localhost" = this computer (your Mac)
        // "9090" = port number (like a door number)
        // "/WebAdder/services/WebAdder" = path to the service
        //
        // WHY PORT 9090?
        // Port 80 is the default for websites, but it requires
        // admin/root permissions. Port 8080 is commonly used
        // for development servers but may already be in use.
        // We use 9090 as an alternative available port.
        // ====================================================
        String url = "http://localhost:9090/WebAdder/services/WebAdder";

        // ====================================================
        // STEP 2: PUBLISH the web service
        // This is the KEY LINE that makes everything work!
        //
        // Endpoint.publish() does the following:
        //   1. Creates a small HTTP server on port 8080
        //   2. Creates an instance of WebAdder class
        //   3. Automatically generates a WSDL document from
        //      the @WebService annotations
        //   4. Starts listening for incoming SOAP requests
        //
        // Parameters:
        //   url → WHERE to publish (the address above)
        //   new WebAdder() → WHAT to publish (our service class)
        //
        // After this line executes, the web service is LIVE
        // and ready to accept requests!
        // ====================================================
        Endpoint.publish(url, new WebAdder());

        // ====================================================
        // STEP 3: Print helpful information for the user
        // ====================================================

        // Print a line of "=" to make the output look nice
        System.out.println("====================================================");

        // Tell the user the service is running
        System.out.println("  WebAdder SOAP Web Service is now RUNNING!");
        System.out.println("====================================================");

        // Print a blank line for readability
        System.out.println();

        // Show the URL where the service is available
        // Clients will send SOAP requests to this address
        System.out.println("  Service URL  : " + url);

        // Show the WSDL URL — adding "?wsdl" to the service URL
        // gives you the WSDL document (the "menu card" of the service).
        // You can open this URL in a browser to see the WSDL XML!
        System.out.println("  WSDL URL     : " + url + "?wsdl");

        System.out.println();

        // Helpful instructions for the user
        System.out.println("  >> Open the WSDL URL in your browser to see the");
        System.out.println("     auto-generated WSDL document!");
        System.out.println();
        System.out.println("  >> Now run WebAdderClient in another terminal");
        System.out.println("     to test the web service.");
        System.out.println();

        // Tell the user how to stop the server
        System.out.println("  >> Press Ctrl+C to stop the server.");
        System.out.println("====================================================");
    }
}

// ============================================================
// WHAT HAPPENS WHEN THIS SERVER RUNS:
//
//   1. Java starts a mini HTTP server on port 8080
//   2. The WSDL document is auto-generated from @WebService
//   3. The server waits for SOAP requests
//   4. When a client sends a SOAP request to the URL:
//      → Server parses the XML
//      → Extracts method name and parameters
//      → Calls the corresponding Java method (addition)
//      → Wraps the result in a SOAP response XML
//      → Sends the response back to the client
//   5. Server keeps running until you press Ctrl+C
//
// The WSDL is automatically available at:
//   http://localhost:8080/WebAdder/services/WebAdder?wsdl
// ============================================================
