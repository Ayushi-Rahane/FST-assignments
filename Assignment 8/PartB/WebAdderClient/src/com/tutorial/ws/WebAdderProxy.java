// ============================================================
// FILE: WebAdderProxy.java
// 
// WHAT IS A PROXY?
// A proxy is a "middleman" or "representative" that acts on
// behalf of someone else. In web services, a proxy class is
// a Java class that HIDES all the complex SOAP communication
// details from the programmer.
//
// WITHOUT a proxy, you would need to:
//   1. Manually create SOAP XML messages
//   2. Send them over HTTP
//   3. Parse the XML response
//   4. Extract the result
// That's a LOT of work!
//
// WITH a proxy, you simply call:
//   proxy.addition(10, 20)  →  returns 30
// The proxy does all the SOAP stuff behind the scenes!
//
// WHO CREATES THIS FILE?
// Eclipse automatically generates this file when we create
// the web service client using the wizard. We don't write
// this by hand!
//
// WHERE IS THIS FILE USED?
// This file is in the "WebAdderClient" project (the CLIENT
// side, not the server side). The test client JSP page uses
// this proxy to call the web service.
// ============================================================

// Package declaration — this class is in the same package
// as the original WebAdder class (com.tutorial.ws)
// This makes it easy to find related classes together
package com.tutorial.ws;

// ============================================================
// IMPORT STATEMENTS
// "import" brings in code from other libraries so we can use
// them. Think of it like borrowing tools from a toolbox.
// ============================================================

// javax.xml.namespace.QName — Represents a "Qualified Name"
// in XML. A qualified name is like a full name with a
// namespace (e.g., "com.tutorial.ws:WebAdder" instead of
// just "WebAdder"). This prevents naming conflicts.
import javax.xml.namespace.QName;

// java.net.URL — Represents a web address (URL).
// We use it to point to the WSDL file location.
import java.net.URL;

// javax.xml.rpc.Service — Represents a web service.
// It's the main object we use to connect to the web service.
import javax.xml.rpc.Service;

// javax.xml.rpc.ServiceFactory — A "factory" that creates
// Service objects. Think of a factory in real life — it
// produces things. This factory produces Service objects.
import javax.xml.rpc.ServiceFactory;

// The Proxy class acts as a bridge between the client and
// the actual web service running on the server.
public class WebAdderProxy {

    // ========================================================
    // INSTANCE VARIABLE: endpoint
    // This stores the URL where our web service is running.
    // It's like storing the address of a shop so you know
    // where to go when you want to buy something.
    // "private" means only this class can access this variable
    // "String" means it stores text data
    // ========================================================
    private String endpoint = null;

    // ========================================================
    // INSTANCE VARIABLE: webAdder
    // This is a reference to the actual web service interface.
    // Once we connect to the web service, this variable holds
    // the connection so we can call methods on it.
    // It's set to "null" initially because we haven't
    // connected yet.
    // ========================================================
    private WebAdder webAdder = null;

    // ========================================================
    // CONSTRUCTOR 1: No-argument constructor
    // A constructor is a special method that runs when you
    // create a new object. "new WebAdderProxy()" will run this.
    // This constructor uses the DEFAULT endpoint URL.
    // ========================================================
    public WebAdderProxy() {
        // Call the other constructor with the default URL
        // This URL points to where the web service is deployed
        // on the Tomcat server running on localhost (your computer)
        // port 8080
        this("http://localhost:8080/WebAdder/services/WebAdder");
    }

    // ========================================================
    // CONSTRUCTOR 2: Constructor with custom endpoint
    // This lets you specify a DIFFERENT URL if the web service
    // is running on a different server or port.
    // ========================================================
    public WebAdderProxy(String endpoint) {
        // Store the endpoint URL for later use
        this.endpoint = endpoint;

        // Call the _initWebAdderProxy() method to set up
        // the connection to the web service
        this._initWebAdderProxy();
    }

    // ========================================================
    // METHOD: _initWebAdderProxy()
    // This PRIVATE method sets up the actual connection to
    // the web service. It reads the WSDL file and creates
    // a connection ("port") to the service.
    //
    // "private" means only this class can call this method
    // (it's an internal helper method, not for outside use)
    // ========================================================
    private void _initWebAdderProxy() {
        try {
            // ================================================
            // STEP 1: Create the WSDL URL
            // We create a URL object pointing to the WSDL file
            // The WSDL URL is the endpoint + "?wsdl"
            // Adding "?wsdl" at the end tells the server to
            // send us the WSDL document (the "menu card")
            // ================================================
            URL wsdlUrl = new URL(endpoint + "?wsdl");

            // ================================================
            // STEP 2: Create a QName (Qualified Name)
            // This uniquely identifies our web service.
            // First parameter: namespace (from our package name)
            // Second parameter: service name
            // Think of QName as the "full official name" of
            // the service — like "Mr. WebAdderService from
            // ws.tutorial.com department"
            // ================================================
            QName serviceName = new QName(
                "http://ws.tutorial.com",      // namespace URI
                "WebAdderService"               // service name
            );

            // ================================================
            // STEP 3: Create a ServiceFactory
            // ServiceFactory is like a tool that knows how to
            // read WSDL files and create connections to web
            // services. We ask it to give us an instance
            // (a ready-to-use tool).
            // ================================================
            ServiceFactory serviceFactory = ServiceFactory.newInstance();

            // ================================================
            // STEP 4: Create the Service object
            // Using the factory, we create a Service object
            // by giving it the WSDL URL and service name.
            // This reads the WSDL file and understands what
            // the web service offers.
            // ================================================
            Service service = serviceFactory.createService(wsdlUrl, serviceName);

            // ================================================
            // STEP 5: Get the "port" (connection to the service)
            // A "port" in WSDL terms is the actual connection
            // point to the web service. We get it by telling
            // the service what Java interface we want to use
            // (WebAdder.class).
            // This returns an object that we can call methods on!
            // ================================================
            webAdder = (WebAdder) service.getPort(WebAdder.class);

        } catch (Exception e) {
            // If anything goes wrong (network error, wrong URL, etc.)
            // print the error details so we can debug
            // "e.printStackTrace()" prints the full error message
            // and shows exactly where the error occurred
            e.printStackTrace();
        }
    }

    // ========================================================
    // METHOD: getEndpoint()
    // A "getter" method that returns the current endpoint URL.
    // Other parts of the code can call this to find out
    // which URL the proxy is connecting to.
    //
    // "public" = anyone can call this
    // "String" = it returns text
    // ========================================================
    public String getEndpoint() {
        return endpoint; // Send back the stored endpoint URL
    }

    // ========================================================
    // METHOD: setEndpoint(String endpoint)
    // A "setter" method that lets you change the endpoint URL.
    // After changing the URL, it re-initializes the connection
    // to point to the new location.
    //
    // WHY WOULD YOU CHANGE THE ENDPOINT?
    // Maybe the web service moved to a different server,
    // or you want to test against a staging server.
    // ========================================================
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint; // Update the stored URL

        // Re-initialize the connection with the new URL
        this._initWebAdderProxy();
    }

    // ========================================================
    // METHOD: getWebAdder()
    // Returns the internal WebAdder service reference.
    // This gives direct access to the web service interface.
    // ========================================================
    public WebAdder getWebAdder() {
        return webAdder;
    }

    // ========================================================
    // METHOD: addition(int nm1, int nm2)
    // THIS IS THE KEY METHOD!
    // When the client calls proxy.addition(10, 20), this
    // method:
    //   1. Takes the two numbers
    //   2. Calls the REAL web service's addition method
    //      (which is running on the Tomcat server)
    //   3. Behind the scenes, a SOAP request is created,
    //      sent over HTTP, a SOAP response is received,
    //      and the result is extracted
    //   4. Returns the result (30)
    //
    // The programmer doesn't need to worry about SOAP at all!
    // They just call this method like any normal Java method.
    //
    // "throws Exception" means this method might fail
    // (e.g., network error) and the caller should handle it
    // ========================================================
    public int addition(int nm1, int nm2) throws Exception {

        // Check if we have a valid connection to the web service
        if (webAdder == null) {
            // If not connected, try to connect again
            _initWebAdderProxy();
        }

        // Call the web service's addition method through the
        // connection and return the result.
        // This single line does A LOT behind the scenes:
        //   1. Creates a SOAP XML request with nm1 and nm2
        //   2. Sends the request to the server via HTTP POST
        //   3. Receives the SOAP XML response
        //   4. Parses the response to extract the integer result
        //   5. Returns the result
        return webAdder.addition(nm1, nm2);
    }
}

// ============================================================
// HOW THE PROXY FITS IN THE BIG PICTURE:
//
//  [Test Client JSP]
//       |
//       | calls proxy.addition(10, 20)
//       ↓
//  [WebAdderProxy]  ← THIS FILE
//       |
//       | creates SOAP request, sends via HTTP
//       ↓
//  [Tomcat Server]
//       |
//       | receives SOAP, calls WebAdder.addition(10, 20)
//       ↓
//  [WebAdder.java]  (on server)
//       |
//       | returns 30
//       ↓
//  [Tomcat Server]
//       |
//       | creates SOAP response with result 30
//       ↓
//  [WebAdderProxy]
//       |
//       | parses response, extracts 30
//       ↓
//  [Test Client JSP]
//       |
//       | displays "Result: 30"
// ============================================================
