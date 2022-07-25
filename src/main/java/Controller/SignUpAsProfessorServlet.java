package Controller;

import java.io.IOException;
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
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/signUpAsProfessorServlet")
public class SignUpAsProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String school = request.getParameter("school");
		String department = request.getParameter("department");
		

		//===================
		
		
		try {
			
			
			String clickButton = request.getParameter("Button");
		
		
			// Return to Log In page if click cancel
			if(clickButton.equals("Cancel"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				
			Statement statement = connection.createStatement();
			String searchUsersql = "SELECT P.user_id \n"
					+ "FROM Professor P \n"
					+ "WHERE P.email = '" + email + "'" +""
					+ "UNION \n"
					+ "SELECT S.user_id \n"
					+ "FROM Student S \n"
					+ "WHERE S.email = '" + email + "';";
					
			
			ResultSet resultSet = statement.executeQuery(searchUsersql);
			
			
			List<String> errList = new LinkedList<String>();
			
			if(email.equals(""))
			{
				errList.add("Email cannot be empty!");
			}
			
			if(password.equals(""))
			{
				errList.add("Password cannot be empty!");
			}
			
			
			if(lName.equals("") || lName.equals(""))
			{
				errList.add("Display name cannot be empty!");
			}
			
			
			if(resultSet.next())
			{
				errList.add("Username already exist!");
			}
			
			if(!errList.isEmpty()) { //has some error
				
				request.setAttribute("errlist", errList);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("signUpAsProfessor.jsp");
				requestDispatcher.forward(request, response);
				connection.close();
				return;
			}
		
			//  Happy Flow success create new user
			String signupUser = "INSERT INTO Professor (pw, email, fname, lname, schoolName, department)"
					+ "VALUES(?,?,?,?,?,?);";
			
			PreparedStatement signupUserPstmt = connection.prepareStatement(signupUser);
			
			signupUserPstmt.setString(1, password);
			signupUserPstmt.setString(2, email);
			signupUserPstmt.setString(3, fName);
			signupUserPstmt.setString(4, lName);
			signupUserPstmt.setString(5, school);
			signupUserPstmt.setString(6, department);
			
			signupUserPstmt.executeUpdate();
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			
			
			requestDispatcher.forward(request, response);
			
			
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	


}
