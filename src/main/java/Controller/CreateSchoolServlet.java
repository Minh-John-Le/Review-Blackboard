package Controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.SchoolDAO;

/**
 * Servlet implementation class CreateSchoolServlet
 */
@WebServlet("/addSchoolServlet")
public class CreateSchoolServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchoolDAO schoolDAO = new SchoolDAO();
  	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sName =request.getParameter("sname");
		String street =request.getParameter("street");
		String city =request.getParameter("city");
		String state =request.getParameter("state");
		String zipcode =request.getParameter("zipcode");
		String clickButton = request.getParameter("Button");
		

		
		if(clickButton != null)
        {
        	if(clickButton.equals("Cancel"))
        	{
        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
    			requestDispatcher.forward(request, response);
    			return;
        	}
        }
		
		List<String> errList = new LinkedList<String>();
		if(sName.equals(""))
		{
			errList.add("School Name cannot be empty!");
		}
		

		if(schoolDAO.doesSchoolExist(sName))
		{
			errList.add("School already exist!");
		}
			

		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("addSchool.jsp");
			requestDispatcher.forward(request, response);

			return;
		}
		
		// add school
		schoolDAO.AddSchoolDAO(sName, street, city, state, zipcode);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
		requestDispatcher.forward(request, response);
	
	}

}