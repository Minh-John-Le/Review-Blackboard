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
		System.out.println("Log In email =" + session.getAttribute("email"));
		
		// Go to Sign up page when click "Sign Up"
		if(clickButton.equals("Sign Up As Student"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpAsStudent.jsp");
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
			String searchUsersql = "SELECT * \n"
					+ "FROM Student\n"
					+ "WHERE email = '" + email + "' and password ='" + password +"'"
					+";";


			ResultSet resultSet = statement.executeQuery(searchUsersql);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homeServlet");
			List<String> errList = new LinkedList<String>();
			
			
			if (!resultSet.next()) 
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
				
			else
			{
				int id = resultSet.getInt("user_id");
				String passwordString = resultSet.getString("password");
				String emailString = resultSet.getString("email");
				String fNameString = resultSet.getString("fname");
				String lNameString = resultSet.getString("lname");
				String majorString = resultSet.getString("major");

				StudentUser currentStudentUser = new StudentUser(id, passwordString, emailString,fNameString,lNameString,majorString);
				
				session.setAttribute("currentStudentUser", currentStudentUser);
				session.setAttribute("currentProfessorUser", null);
				
				requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.forward(request, response);
				connection.close();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}