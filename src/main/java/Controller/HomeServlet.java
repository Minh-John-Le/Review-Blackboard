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
import Beans.School;

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
					
			
			
			if (clickButton.equals("Search School")) {
				
				LinkedList<School> searchSchool = this.SearchSchool(school);
				
				if(!searchSchool.isEmpty())
				{
					session.setAttribute("searchSchoolList", searchSchool);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("schoolSearchResult.jsp");		
					requestDispatcher.forward(request, response);
					return;
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.include(request, response);
				return;
				
			}
			
			else if(clickButton.equals("Search Professor"))
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
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
	
	private LinkedList<School> SearchSchool (String schoolName)
	{
		LinkedList<School> schoolList = new LinkedList<School>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String searchSchoolsql = "SELECT *\r\n"
					+ "FROM school S\r\n"
					+ "WHERE S.sname LIKE '" + schoolName +"%' ;";
			
			ResultSet SchoolSearchResult = statement.executeQuery(searchSchoolsql);

			while(SchoolSearchResult.next())
			{
				int schoolID = SchoolSearchResult.getInt("school_id");
				String sname = SchoolSearchResult.getString("sname");
				String street = SchoolSearchResult.getString("street");
				String city = SchoolSearchResult.getString("city");
				String state = SchoolSearchResult.getString("state");		
				String zipcode = SchoolSearchResult.getString("zipcode");
				
				
				School school = new School(sname,street,city,state,zipcode,schoolID);
				schoolList.add(school);
				
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return schoolList;
	}
}
