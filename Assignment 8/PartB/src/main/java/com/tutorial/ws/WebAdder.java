// ============================================================
// FILE: WebAdder.java (RUNNABLE version with JAX-WS annotations)
//
// This is the MAIN Web Service class — the HEART of our project.
// It contains the "addition" method that adds two numbers.
//
// WHAT CHANGED FROM THE ECLIPSE VERSION?
// We added JAX-WS annotations (@WebService, @WebMethod) so that
// Java knows this class should be published as a SOAP web service.
// In Eclipse, the wizard adds these automatically. Here, we add
// them ourselves since we're running without Eclipse.
//
// WHAT IS AN ANNOTATION?
// An annotation starts with "@" and gives EXTRA INFORMATION
// to Java about a class or method. It's like putting a sticky
// note on your code saying "Hey Java, treat this specially!"
//
// For example:
//   @WebService  →  "Hey Java, this class is a web service!"
//   @WebMethod   →  "Hey Java, this method can be called remotely!"
// ============================================================

// "package" tells Java which folder this file belongs to.
// Think of it like an address: com > tutorial > ws
// This helps organize code and avoid naming conflicts.
package com.tutorial.ws;

// ============================================================
// IMPORT STATEMENTS
// "import" brings in tools/classes from other libraries
// so we can use them in our code.
// ============================================================

// @WebService annotation — marks a class as a SOAP web service
// When Java sees this annotation, it knows to generate all the
// SOAP infrastructure (WSDL, message handling, etc.) for this class.
import javax.jws.WebService;

// @WebMethod annotation — marks a method as a web service operation
// Only methods with this annotation will be available for remote
// clients to call via SOAP. Other methods remain private/internal.
import javax.jws.WebMethod;

// @WebParam annotation — gives a name to method parameters
// Without this, parameters would have generic names like "arg0"
// in the WSDL. With this, we can give them meaningful names
// like "nm1" and "nm2".
import javax.jws.WebParam;

// ============================================================
// @WebService ANNOTATION
// This is the MOST IMPORTANT line!
// It tells Java: "Make this class available as a SOAP web service"
//
// What happens behind the scenes when Java sees @WebService:
//   1. Java generates a WSDL document describing this service
//   2. Java creates a SOAP endpoint that listens for requests
//   3. Java sets up XML-to-Java conversion (marshalling)
//   4. Java sets up Java-to-XML conversion (unmarshalling)
//
// Parameters of @WebService:
//   serviceName = "WebAdderService"
//     → The name of the web service (appears in WSDL)
//     → Clients use this name to find the service
//
//   targetNamespace = "http://ws.tutorial.com"
//     → A unique identifier for the service (like a domain)
//     → Prevents naming conflicts with other services
//     → Convention: reverse of the package name
//       com.tutorial.ws → http://ws.tutorial.com
// ============================================================
@WebService(
    serviceName = "WebAdderService",           // Name of the service
    targetNamespace = "http://ws.tutorial.com"  // Unique namespace
)
// "public" means this class can be accessed from anywhere.
// Any other program, including web service clients, can use it.
public class WebAdder {

    // ========================================================
    // @WebMethod ANNOTATION
    // This tells Java: "Make this method callable via SOAP"
    //
    // When a client wants to call this method:
    //   1. Client creates a SOAP XML request with nm1, nm2
    //   2. Client sends request to server via HTTP POST
    //   3. Server receives request, extracts nm1, nm2
    //   4. Server calls this addition() method
    //   5. Server wraps the result in a SOAP XML response
    //   6. Server sends response back to client
    //
    // operationName = "addition"
    //   → The name of this operation in the WSDL document
    //   → Clients will call this operation by this name
    // ========================================================
    @WebMethod(operationName = "addition")   // Make this a web method
    public int addition(
        // @WebParam gives names to our parameters in the WSDL
        // Without this, they'd be called "arg0" and "arg1"
        // which is confusing. "nm1" and "nm2" are more readable.
        @WebParam(name = "nm1") int nm1,  // First number to add
        @WebParam(name = "nm2") int nm2   // Second number to add
    ) {
        // This line adds the two numbers and immediately
        // returns the result to whoever called this method.
        // "return" means "send this value back to the caller".
        // "nm1 + nm2" is simple arithmetic addition.
        //
        // Behind the scenes (SOAP flow):
        //   - This return value (e.g., 30) gets wrapped into
        //     a SOAP XML response like:
        //     <additionResponse>
        //       <return>30</return>
        //     </additionResponse>
        //   - This XML is sent back to the client over HTTP
        return nm1 + nm2;
    }

    // ========================================================
    // You can add MORE web service methods here if needed.
    // For example:
    //
    // @WebMethod(operationName = "subtraction")
    // public int subtraction(
    //     @WebParam(name = "nm1") int nm1,
    //     @WebParam(name = "nm2") int nm2
    // ) {
    //     return nm1 - nm2;
    // }
    //
    // Each @WebMethod will appear in the WSDL document and
    // can be called by clients separately.
    // ========================================================
}

// ============================================================
// SUMMARY OF SOAP WEB SERVICE FLOW:
//
// 1. We write this Java class with @WebService annotation
// 2. Java automatically generates a WSDL file describing it
// 3. The service is published at an endpoint URL
// 4. A client reads the WSDL to know what methods exist
// 5. Client sends a SOAP REQUEST (XML) to call "addition"
// 6. Server processes the request and sends back SOAP RESPONSE
// 7. Client reads the response and gets the result
//
// SOAP REQUEST example (what client sends):
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
//                   xmlns:ws="http://ws.tutorial.com">
//   <soapenv:Body>
//     <ws:addition>
//       <nm1>10</nm1>
//       <nm2>20</nm2>
//     </ws:addition>
//   </soapenv:Body>
// </soapenv:Envelope>
//
// SOAP RESPONSE example (what server sends back):
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
//   <soapenv:Body>
//     <additionResponse>
//       <return>30</return>
//     </additionResponse>
//   </soapenv:Body>
// </soapenv:Envelope>
// ============================================================
