// ============================================================
// FILE: WebAdder.java
// This is the MAIN Web Service class.
// Whatever methods we write inside this class will become
// "web service methods" — meaning other programs (clients)
// can call these methods over the internet using SOAP protocol.
// ============================================================

// "package" tells Java which folder this file belongs to.
// Think of it like an address: com > tutorial > ws
// This helps organize code and avoid naming conflicts.
package com.tutorial.ws;

// ----------------------------------------------------------
// WHAT IS A WEB SERVICE?
// A web service is a program running on a server that other
// programs can call over the internet. It's like a function
// that lives on the internet — anyone can send it input and
// get output back, regardless of what programming language
// they use.
// ----------------------------------------------------------

// ----------------------------------------------------------
// WHAT IS SOAP?
// SOAP = Simple Object Access Protocol
// It's a set of rules (protocol) for sending messages between
// computers over the internet. The messages are written in XML
// format. Think of SOAP as a "letter format" — every letter
// (message) must follow a specific structure (envelope, header,
// body) so that both sender and receiver understand it.
// ----------------------------------------------------------

// ----------------------------------------------------------
// WHAT IS WSDL?
// WSDL = Web Services Description Language
// It's an XML file that describes WHAT a web service can do.
// Think of it as a "menu card" at a restaurant — it tells you
// what "dishes" (methods) are available, what "ingredients"
// (parameters) each dish needs, and what you'll "get back"
// (return type). Clients read the WSDL to understand how to
// call the web service.
// ----------------------------------------------------------

// ----------------------------------------------------------
// WHAT IS UDDI?
// UDDI = Universal Description, Discovery, and Integration
// It's like a "Yellow Pages" (phone directory) for web services.
// Companies can register their web services in UDDI so that
// other companies can find and use them. In practice, UDDI is
// rarely used today, but it's an important concept to know.
// ----------------------------------------------------------

// "public" means this class can be accessed from anywhere.
// Any other program, including web service clients, can use it.
public class WebAdder {

    // ======================================================
    // This is our web service METHOD called "addition".
    // When we convert this class into a web service (using
    // Eclipse's wizard), this method becomes available for
    // remote clients to call over SOAP protocol.
    //
    // PARAMETERS:
    //   nm1 - the first number (integer) sent by the client
    //   nm2 - the second number (integer) sent by the client
    //
    // RETURNS:
    //   The sum of nm1 and nm2 as an integer
    //
    // HOW IT WORKS IN SOAP:
    //   1. Client sends a SOAP REQUEST (XML message) containing
    //      nm1 and nm2 values
    //   2. Server receives the SOAP request, extracts nm1 & nm2
    //   3. Server runs this addition() method
    //   4. Server sends back a SOAP RESPONSE (XML message)
    //      containing the result
    // ======================================================
    public int addition(int nm1, int nm2) {

        // This line adds the two numbers and immediately
        // returns the result to whoever called this method.
        // "return" means "send this value back to the caller".
        // "nm1 + nm2" is simple arithmetic addition.
        return nm1 + nm2;
    }

    // ======================================================
    // You can add MORE web service methods here if needed.
    // For example, subtraction, multiplication, etc.
    // Each public method in this class will appear in the
    // WSDL document and can be called by clients.
    // ======================================================
}

// ============================================================
// SUMMARY OF SOAP WEB SERVICE FLOW:
//
// 1. We write this Java class with public methods
// 2. Eclipse generates a WSDL file describing this service
// 3. The service is deployed on Tomcat server
// 4. A client reads the WSDL to know what methods are available
// 5. Client sends a SOAP REQUEST (XML) to call "addition"
// 6. Server processes the request and sends back SOAP RESPONSE
// 7. Client reads the response and gets the result
//
// SOAP REQUEST example (what client sends):
// <soapenv:Envelope>
//   <soapenv:Body>
//     <addition>
//       <nm1>10</nm1>
//       <nm2>20</nm2>
//     </addition>
//   </soapenv:Body>
// </soapenv:Envelope>
//
// SOAP RESPONSE example (what server sends back):
// <soapenv:Envelope>
//   <soapenv:Body>
//     <additionResponse>
//       <additionReturn>30</additionReturn>
//     </additionResponse>
//   </soapenv:Body>
// </soapenv:Envelope>
// ============================================================
