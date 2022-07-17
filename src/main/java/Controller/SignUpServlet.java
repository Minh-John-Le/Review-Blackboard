package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String displayName = request.getParameter("displayName");
		String password = request.getParameter("password");
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				
			Statement statement = connection.createStatement();
			String searchUsersql = "SELECT * "
					+ "FROM Student_User "
					+ "WHERE email = '" + email + "' and password ='" + password +"'"
					+";";
					
			
			ResultSet resultSet = statement.executeQuery(searchUsersql);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUp.html");
			
			if (resultSet.next()) {
				//request.setAttribute("message", "Welcome to Interservlet Communication " + email);
				requestDispatcher.forward(request, response);
			} else {
				String signupUser = "INSERT INTO STUDENT_USER "
						+ "VALUES(" + email + "," + displayName + "," + password + ");";
				statement.executeUpdate(signupUser);
				
				requestDispatcher = request.getRequestDispatcher("login.html");
				requestDispatcher.include(request, response);
			}
			
			
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}


}
