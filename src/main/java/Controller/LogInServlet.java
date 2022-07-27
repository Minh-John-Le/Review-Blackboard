package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.servlet.http.HttpSession;

import Beans.ProfessorUser;
import Beans.StudentUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context;

	public void init(ServletConfig config)
	{		

		context = config.getServletContext();		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		
		// Go to Sign up page when click "Sign Up"
		if(clickButton.equals("Sign Up As Student"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpAsStudent.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		if(clickButton.equals("Sign Up As Professor"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
			session.setAttribute("searchProfessorList", null);
			requestDispatcher.forward(request, response);
			return;
		}

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));



			Statement statement = connection.createStatement();
			String searchStudentsql = "SELECT * \n"
					+ "FROM Student\n"
					+ "WHERE email = '" + email + "' and password ='" + password +"'"
					+";";


			ResultSet studentSearchResult = statement.executeQuery(searchStudentsql);
			
			Statement statement2 = connection.createStatement();
			String searchProfessorsql = "SELECT * \n"
					+ "FROM Professor\n"
					+ "WHERE email = '" + email + "' and pw ='" + password +"'"
					+";";


			ResultSet professorSearchResult = statement2.executeQuery(searchProfessorsql);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homeServlet");
			List<String> errList = new LinkedList<String>();
			
			if (password.equals(""))
			{
				errList.add("Password cannot be empty!");
			}
			
			if (studentSearchResult.next())
			{
				session.setAttribute("userRole", "student");
			}
			else if (professorSearchResult.next())
			{
				session.setAttribute("userRole", "professor");
			}
			else
			{
				errList.add("Username or Password is inccorect!");
			} 
			
			
			if(!errList.isEmpty()) // no error prceed to home page
			{
				request.setAttribute("errlist", errList);
				requestDispatcher = request.getRequestDispatcher("login.jsp");
				requestDispatcher.include(request, response);
				connection.close();
				return;
			}
				
			if(session.getAttribute("userRole").equals("student"))
			{
				int id = studentSearchResult.getInt("user_id");
				String passwordString = studentSearchResult.getString("password");
				String emailString = studentSearchResult.getString("email");
				String fNameString = studentSearchResult.getString("fname");
				String lNameString = studentSearchResult.getString("lname");
				String majorString = studentSearchResult.getString("major");
				

				StudentUser currentStudentUser = new StudentUser(id, passwordString, emailString,fNameString,lNameString,majorString);
				
				session.setAttribute("currentStudentUser", currentStudentUser);
				session.setAttribute("currentProfessorUser", null);
				session.setAttribute("name", fNameString + " " + lNameString);
				session.setAttribute("userId", currentStudentUser.getId());
				
				requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.forward(request, response);
				connection.close();
				return;
			}
			
			if(session.getAttribute("userRole").equals("professor"))
			{
				
				System.out.println("Professor User log in sucess");
				int idString = professorSearchResult.getInt("user_id");
				String passwordString = professorSearchResult.getString("pw");
				String emailString = professorSearchResult.getString("email");
				String fNameString = professorSearchResult.getString("fname");
				String lNameString = professorSearchResult.getString("lname");
				String schoolString = professorSearchResult.getString("schoolName");
				String deparmentString = professorSearchResult.getString("department");
				
				System.out.println("user Id is " + idString);

				ProfessorUser currentProfessorUser = new ProfessorUser(idString, passwordString, emailString,fNameString,lNameString,schoolString,deparmentString);
				
				session.setAttribute("currentStudentUser", null);
				session.setAttribute("currentProfessorUser", currentProfessorUser);
				session.setAttribute("name", fNameString + " " + lNameString);
				session.setAttribute("userId", currentProfessorUser.getId());
				
				requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.forward(request, response);
				connection.close();
				return;
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}