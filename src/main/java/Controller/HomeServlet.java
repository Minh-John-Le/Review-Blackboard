package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		
		// Go to Sign up page when click "Sign Up"
		if(clickButton.equals("Search Professor"))
		{
			
		}


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
