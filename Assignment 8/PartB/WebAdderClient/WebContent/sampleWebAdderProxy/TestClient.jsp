<%-- ============================================================ --%>
<%-- FILE: TestClient.jsp (Test Client for WebAdder Web Service)  --%>
<%--                                                              --%>
<%-- WHAT IS JSP?                                                 --%>
<%-- JSP = JavaServer Pages                                       --%>
<%-- It's a technology that lets you mix HTML and Java code in    --%>
<%-- one file. The server runs the Java code and generates HTML   --%>
<%-- that is sent to the browser. Think of it as a template:     --%>
<%-- some parts are fixed HTML, and some parts are dynamic Java  --%>
<%-- code that produces different results each time.             --%>
<%--                                                              --%>
<%-- WHAT DOES THIS FILE DO?                                      --%>
<%-- This is the TEST CLIENT — a web page where you can:         --%>
<%--   1. See the available web service methods (like "addition") --%>
<%--   2. Enter input values (nm1 and nm2)                       --%>
<%--   3. Click "Invoke" to call the web service                 --%>
<%--   4. See the result on the same page                        --%>
<%--                                                              --%>
<%-- WHO CREATES THIS FILE?                                       --%>
<%-- Eclipse generates this file automatically when we select    --%>
<%-- "Test client" in the web service wizard.                    --%>
<%-- ============================================================ --%>

<%-- "page import" tells JSP which Java classes we need to use --%>
<%-- We import the proxy class so we can call the web service --%>
<%@ page import="com.tutorial.ws.WebAdderProxy" %>

<%-- Standard HTML page structure begins here --%>
<html>
<head>
    <!-- Title that appears in the browser tab -->
    <title>WebAdder Web Service Test Client</title>

    <!-- CSS styling to make the page look presentable -->
    <!-- "style" block contains CSS rules that control appearance -->
    <style>
        /* Style for the entire page body */
        body {
            font-family: Arial, sans-serif;  /* Use a clean, readable font */
            margin: 20px;                      /* Add space around the edges */
            background-color: #f5f5f5;        /* Light gray background */
        }

        /* Style for the heading */
        h1 {
            color: #333;                       /* Dark gray text color */
        }

        /* Style for the table that displays form fields */
        table {
            border-collapse: collapse;         /* Remove gaps between cells */
            margin: 10px 0;                    /* Space above and below table */
        }

        /* Style for table cells */
        td {
            padding: 5px 10px;                /* Space inside each cell */
        }

        /* Style for input text fields */
        input[type="text"] {
            padding: 5px;                      /* Space inside the input box */
            border: 1px solid #ccc;           /* Light gray border */
            border-radius: 3px;               /* Slightly rounded corners */
        }

        /* Style for the Invoke button */
        input[type="submit"] {
            background-color: #4CAF50;        /* Green background */
            color: white;                      /* White text */
            padding: 8px 16px;                /* Space inside the button */
            border: none;                      /* No border */
            border-radius: 4px;               /* Rounded corners */
            cursor: pointer;                   /* Show hand cursor on hover */
        }

        /* Style for the result display area */
        .result {
            background-color: #e8f5e9;        /* Light green background */
            padding: 15px;                     /* Space inside the box */
            border-radius: 5px;               /* Rounded corners */
            margin-top: 10px;                  /* Space above */
            border: 1px solid #4CAF50;        /* Green border */
        }
    </style>
</head>

<body>
    <!-- Main heading of the test client page -->
    <h1>WebAdder Web Service Test Client</h1>

    <!-- Brief description of what this page does -->
    <p>This client lets you test the WebAdder SOAP web service.
       Click on a method below to test it.</p>

    <hr/> <!-- Horizontal line to visually separate sections -->

    <!-- ======================================================== -->
    <!-- SECTION: Available Methods                                -->
    <!-- This shows which methods are available in the web service -->
    <!-- The user clicks on a method name to test it              -->
    <!-- ======================================================== -->
    <h2>Available Methods:</h2>

    <%-- 
        This is a JSP link that, when clicked, reloads this page
        with a parameter "method=addition". This tells the page
        that the user wants to test the "addition" method.
        
        "?" starts the query parameters in a URL
        "method=addition" is a key-value pair being sent to the page
    --%>
    <ul>
        <li>
            <a href="TestClient.jsp?method=addition">
                <strong>int addition(int nm1, int nm2)</strong>
            </a>
            — Adds two integers and returns the sum
        </li>
    </ul>

    <hr/>

    <%-- ======================================================== --%>
    <%-- JSP SCRIPTLET: Java code that runs on the server          --%>
    <%-- <% ... %> contains Java code                              --%>
    <%-- This code checks if the user clicked on a method and      --%>
    <%-- shows the appropriate form or result                      --%>
    <%-- ======================================================== --%>
    <%
        // Get the "method" parameter from the URL
        // If the user clicked on "addition" link, method will be "addition"
        // If the user just opened the page, method will be null
        String method = request.getParameter("method");

        // Check if the user has selected a method to test
        if ("addition".equals(method)) {
            // ====================================================
            // The user clicked on "addition" — show the input form
            // ====================================================

            // Get the "action" parameter — this tells us if the
            // user has already filled in the form and clicked "Invoke"
            String action = request.getParameter("action");

            // If action is null, the user hasn't submitted the form yet
            // So we show the input form
            if (action == null) {
    %>
                <!-- ============================================ -->
                <!-- INPUT FORM: Shows text fields for nm1 and nm2 -->
                <!-- The user enters two numbers here              -->
                <!-- ============================================ -->
                <h2>Test: addition(int nm1, int nm2)</h2>

                <%-- "form" creates an HTML form that sends data --%>
                <%-- "method=GET" means data is sent in the URL --%>
                <%-- "action=TestClient.jsp" means submit to this same page --%>
                <form method="GET" action="TestClient.jsp">

                    <%-- Hidden fields: these are invisible but send --%>
                    <%-- important data along with the form --%>
                    <%-- They tell the page that we're testing the --%>
                    <%-- "addition" method and that the form was "invoked" --%>
                    <input type="hidden" name="method" value="addition"/>
                    <input type="hidden" name="action" value="invoke"/>

                    <table>
                        <tr>
                            <%-- Label for first input field --%>
                            <td><strong>nm1 (int):</strong></td>
                            <%-- Text input where user types the first number --%>
                            <td><input type="text" name="nm1" size="10"/></td>
                        </tr>
                        <tr>
                            <%-- Label for second input field --%>
                            <td><strong>nm2 (int):</strong></td>
                            <%-- Text input where user types the second number --%>
                            <td><input type="text" name="nm2" size="10"/></td>
                        </tr>
                    </table>

                    <br/>

                    <%-- The "Invoke" button — clicking this submits the form --%>
                    <%-- and sends nm1, nm2 values to this page for processing --%>
                    <input type="submit" value="Invoke"/>

                </form>
    <%
            } else if ("invoke".equals(action)) {
                // ================================================
                // The user clicked "Invoke" — call the web service
                // and show the result!
                // ================================================

                // STEP 1: Get the user's input from the form
                // request.getParameter() retrieves the value the user typed
                // Integer.parseInt() converts the text to a number
                // (because form data always comes as text/String)
                String nm1Str = request.getParameter("nm1");
                String nm2Str = request.getParameter("nm2");

                // Convert String to int (integer)
                int nm1 = Integer.parseInt(nm1Str);
                int nm2 = Integer.parseInt(nm2Str);

                // STEP 2: Create a proxy object to connect to the web service
                // "new WebAdderProxy()" creates a new proxy that automatically
                // connects to the web service at localhost:8080
                WebAdderProxy proxy = new WebAdderProxy();

                // STEP 3: Call the web service method through the proxy
                // This single line does ALL the SOAP magic:
                //   - Creates SOAP XML request
                //   - Sends it to the server
                //   - Gets SOAP XML response
                //   - Extracts the result
                int result = proxy.addition(nm1, nm2);
    %>
                <!-- ============================================ -->
                <!-- RESULT DISPLAY: Show the web service result   -->
                <!-- ============================================ -->
                <h2>Result:</h2>

                <div class="result">
                    <p><strong>Method called:</strong> addition(<%= nm1 %>, <%= nm2 %>)</p>
                    <%-- "<%= ... %>" is a JSP expression that prints a value --%>
                    <%-- It's like System.out.println() but for web pages --%>
                    <p><strong>SOAP Response Result:</strong>
                        <span style="font-size: 24px; color: #2E7D32;">
                            <%= result %>
                        </span>
                    </p>
                </div>

                <br/>
                <%-- Link to go back and test again with different values --%>
                <a href="TestClient.jsp?method=addition">&#8592; Test again</a>
                &nbsp;|&nbsp;
                <a href="TestClient.jsp">&#8592; Back to method list</a>
    <%
            } // end of if-else for action
        } // end of if for method
    %>

    <!-- ======================================================== -->
    <!-- Footer with helpful information -->
    <!-- ======================================================== -->
    <hr/>
    <p style="color: #666; font-size: 12px;">
        <strong>WSDL URL:</strong>
        <a href="http://localhost:8080/WebAdder/services/WebAdder?wsdl">
            http://localhost:8080/WebAdder/services/WebAdder?wsdl
        </a>
        <br/>
        <strong>Note:</strong> This test client communicates with the
        WebAdder SOAP web service running on Tomcat at localhost:8080.
    </p>

</body>
</html>

<%-- ============================================================ --%>
<%-- SUMMARY OF HOW THIS TEST CLIENT WORKS:                       --%>
<%--                                                              --%>
<%-- 1. User opens this page in a browser                         --%>
<%-- 2. User sees list of available methods (just "addition")     --%>
<%-- 3. User clicks "addition" → form appears with nm1, nm2 fields --%>
<%-- 4. User enters two numbers and clicks "Invoke"              --%>
<%-- 5. The JSP code creates a WebAdderProxy object              --%>
<%-- 6. Proxy sends a SOAP request to the server                 --%>
<%-- 7. Server runs addition() and sends SOAP response           --%>
<%-- 8. Proxy extracts the result                                --%>
<%-- 9. Result is displayed on the page                          --%>
<%--                                                              --%>
<%-- The entire SOAP communication is hidden from the user!       --%>
<%-- They just see a simple web form with input fields.           --%>
<%-- ============================================================ --%>
