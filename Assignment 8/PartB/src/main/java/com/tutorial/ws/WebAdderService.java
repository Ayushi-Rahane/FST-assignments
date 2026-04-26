// ============================================================
// FILE: WebAdderService.java (Service Endpoint Interface - SEI)
//
// WHAT IS A SERVICE ENDPOINT INTERFACE (SEI)?
// In JAX-WS, the client needs an INTERFACE (not a class) to
// connect to a web service. An interface is like a "contract"
// — it defines WHAT methods exist but not HOW they work.
//
// WHY CAN'T WE USE THE CLASS DIRECTLY?
// Java's proxy mechanism (used by JAX-WS to create client
// connections) only works with interfaces. When the client
// calls service.getPort(WebAdderService.class), Java creates
// a "proxy" object that implements this interface and
// forwards all method calls as SOAP messages to the server.
//
// Think of it like ordering from a delivery app:
//   - The interface is the MENU (lists what you can order)
//   - The actual restaurant (WebAdder.class) cooks the food
//   - The delivery app (proxy) connects you to the restaurant
// ============================================================

// Package declaration
package com.tutorial.ws;

// Import JAX-WS annotations
import javax.jws.WebMethod;        // Marks a method as a web service operation
import javax.jws.WebParam;         // Names the parameters in the WSDL
import javax.jws.WebService;       // Marks this as a web service interface
import javax.jws.WebResult;        // Names the return value in the WSDL

// ============================================================
// @WebService annotation on an INTERFACE
// This tells JAX-WS: "This interface describes a web service"
//
// The client uses this interface to know what methods are
// available on the web service, without needing the actual
// server-side code.
//
// name = "WebAdder"
//   → The name of the port type in WSDL
//
// targetNamespace = "http://ws.tutorial.com"
//   → Must match the namespace used by the server!
//   → If they don't match, the client can't find the service
// ============================================================
@WebService(
    name = "WebAdder",                        // Port type name (matches server)
    targetNamespace = "http://ws.tutorial.com" // Must match server namespace!
)
public interface WebAdderService {

    // ========================================================
    // METHOD DECLARATION: addition
    //
    // This declares WHAT the method looks like — its name,
    // parameters, and return type. The actual code (nm1 + nm2)
    // lives on the SERVER side in WebAdder.java.
    //
    // When the client calls this method on the proxy:
    //   1. The proxy creates a SOAP XML request
    //   2. Sends it to the server
    //   3. Server runs the real addition() method
    //   4. Server sends back a SOAP XML response
    //   5. Proxy extracts the result and returns it
    //
    // The @WebMethod and @WebParam annotations must match
    // what the server uses, so the SOAP messages are compatible.
    // ========================================================
    @WebMethod(operationName = "addition")           // Operation name in WSDL
    @WebResult(name = "return")                       // Name of the return value in WSDL
    int addition(
        @WebParam(name = "nm1") int nm1,             // First parameter name
        @WebParam(name = "nm2") int nm2              // Second parameter name
    );
}

// ============================================================
// SUMMARY:
// This INTERFACE is used by the CLIENT to connect to the
// web service. The SERVER uses the WebAdder CLASS.
//
//   Server side: WebAdder.java (class with actual code)
//   Client side: WebAdderService.java (interface, no code)
//
// Both must have matching method signatures (same names,
// same parameter types, same return type) for SOAP to work.
// ============================================================
