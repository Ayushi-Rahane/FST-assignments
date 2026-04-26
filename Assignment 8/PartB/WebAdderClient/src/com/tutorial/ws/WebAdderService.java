// ============================================================
// FILE: WebAdderService.java (Service Locator Interface)
//
// WHAT IS THIS FILE?
// This is a Java INTERFACE that defines the methods needed
// to locate (find) and connect to the WebAdder web service.
//
// WHAT IS AN INTERFACE?
// An interface in Java is like a "promise" or "contract".
// It says "any class that implements me MUST provide these
// methods". It defines WHAT methods should exist, but not
// HOW they work.
//
// Think of it like a job description: it lists what skills
// are required, but each person (class) implements those
// skills in their own way.
//
// WHY DO WE NEED THIS?
// This interface is part of the "Service Locator" pattern.
// It helps the client find the web service by providing
// methods to get the service's URL and connection.
//
// WHO CREATES THIS FILE?
// Eclipse generates this file automatically when we create
// the web service client.
// ============================================================

// Package declaration — same package as other web service files
package com.tutorial.ws;

// Import the remote exception class
// RemoteException is thrown when something goes wrong with
// the network communication (e.g., server is down, timeout)
import java.rmi.RemoteException;

// Import the Service interface from JAX-RPC
// JAX-RPC = Java API for XML-based Remote Procedure Calls
// It provides the base interface for web service connections
import javax.xml.rpc.Service;

// Import ServiceException — thrown when there's a problem
// creating or connecting to the web service
import javax.xml.rpc.ServiceException;

// "extends Service" means this interface ADDS MORE methods
// on top of what the standard Service interface already provides.
// Think of it as: "I am a Service, BUT I also have these
// extra methods specific to WebAdder."
public interface WebAdderService extends Service {

    // ========================================================
    // METHOD: getWebAdderAddress()
    // Returns the URL (as a String) where the WebAdder web
    // service is running.
    //
    // Example return value:
    //   "http://localhost:8080/WebAdder/services/WebAdder"
    //
    // WHY IS THIS USEFUL?
    // The client needs to know WHERE to send SOAP requests.
    // This method provides that address.
    //
    // "throws ServiceException" means this method might fail
    // if there's a problem with the service configuration.
    // ========================================================
    public String getWebAdderAddress() throws ServiceException;

    // ========================================================
    // METHOD: getWebAdder()
    // Returns a "WebAdder" object that the client can use to
    // call web service methods (like addition()).
    //
    // This is the main method clients use to get a connection
    // to the web service. Once you have this object, you can
    // call: webAdder.addition(10, 20)
    //
    // "throws ServiceException" — in case the service can't
    // be created (wrong WSDL, missing configuration, etc.)
    // ========================================================
    public WebAdder getWebAdder() throws ServiceException;
}

// ============================================================
// SUMMARY:
// This interface defines two key methods:
//   1. getWebAdderAddress() → tells you WHERE the service is
//   2. getWebAdder() → gives you a CONNECTION to the service
//
// Eclipse generates a concrete class that IMPLEMENTS this
// interface and fills in the actual code for these methods.
// ============================================================
