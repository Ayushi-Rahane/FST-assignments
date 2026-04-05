package com.controller;

import com.model.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;


public class StudentServlet extends HttpServlet {

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	   int rollno = Integer.parseInt(request.getParameter("rollno"));
	   StudentDAO dao = new StudentDAO();
	   Student_data_container s = dao.getStudent(rollno);
	   request.setAttribute("student",s ); //Send data to JSP and store the object in request	
	   
	   //Forwards the data to JSP
	   RequestDispatcher rd = request.getRequestDispatcher("result.jsp"); //Sending cntrol to JSP, where jsp will display the data
	   rd.forward(request, response);
	   
	   
	   
   }
}