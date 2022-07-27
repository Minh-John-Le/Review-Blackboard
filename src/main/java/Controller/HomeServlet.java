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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parametter
		String clickButton = request.getParameter("Button");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String school = request.getParameter("school");
		LinkedList<Professor> professorList = new LinkedList<Professor>();
		
		HttpSession session = request.getSession();
		
		
		// Go to Sign up page when click "Sign Up"
		
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
			
			if (clickButton.equals("Add Professor"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddProfessor.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			if (clickButton.equals("Add School"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("addSchool.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			if (clickButton.equals("Support"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("supportTicket.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
		
		}

		try {
					
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			
			if (clickButton.equals("Search School")) {
				RequestDispatcher rd = request.getRequestDispatcher("/collegeSearchServlet");
				rd.forward(request, response);
			}
			
			else if(clickButton.equals("Search Professor"))
			{
				Statement statement = connection.createStatement();
				String searchProfessorsql = "SELECT *\r\n"
						+ "FROM professor P\r\n"
						+ "WHERE P.fname LIKE '" + fname +"%' \r\n"
						+ "AND P.lname LIKE '" + lname +"%' \r\n"
						+ "AND P.schoolName Like '" + school + "%';";	
				
				ResultSet ProfessorSearchResult = statement.executeQuery(searchProfessorsql);

				while(ProfessorSearchResult.next())
				{
					int user_IdString = ProfessorSearchResult.getInt("user_ID");
					String fnameString =  ProfessorSearchResult.getString("fname");
					String lnameString =  ProfessorSearchResult.getString("lname");
					String schoolString = ProfessorSearchResult.getString("schoolName");
					String emailString = ProfessorSearchResult.getString("email");
					Professor professor = new Professor(user_IdString, fnameString, lnameString, schoolString,emailString);
					professorList.add(professor);
					
					
				}
				
				if (!professorList.isEmpty())
				{
					session.setAttribute("searchProfessorList", professorList);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorSearchResult.jsp");
					requestDispatcher.include(request, response);
					connection.close();
					return;
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.include(request, response);
				connection.close();
				
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
