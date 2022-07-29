package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Professor;
import Beans.ProfessorReview;
import Beans.School;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/schoolSearchResultServlet")
public class SchoolSearchResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parameter
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();
		
		LinkedList<School> searchSchoolList = (LinkedList<School>) (session.getAttribute("searchSchoolList"));
		
		
		
		if (clickButton != null)
		{
			// if click a button then process
			if (clickButton.equals("Review Blackboard"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			if (clickButton.equals("Log Out"))
			{
				session.setAttribute("currentStudentUser", null);
				session.setAttribute("currentProfessorUser", null);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
		}
		
		
		// Check is user click any view button
		int index = 0;
		while(index < searchSchoolList.size())
		{
			String schoolName = searchSchoolList.get(index).getName();
			String id = String.valueOf(searchSchoolList.get(index).getSchoolId());
			String view = "view" + id;
			
			if(request.getParameter(view) != null)
			{
				session.setAttribute("selectSchoolName",schoolName);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/collegeSearchServlet");
				rd.forward(request, response);
				
				return;
						
			}
			index++;			
		}
		
	}
	
}
