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

import Beans.Professor;

/**
 * Servlet implementation class setUpProfessorPasswordServlet
 */
@WebServlet("/setUpProfessorPasswordServlet")
public class SetUpProfessorPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context;

	public void init(ServletConfig config)
	{		

		context = config.getServletContext();		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();	
		String password = request.getParameter("password");
		Professor professor =  (Professor)session.getAttribute("selectedProfessor");
		String id = String.valueOf(professor.getUser_ID());
		
		if(clickButton != null && clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
			requestDispatcher.include(request, response);
			
			return;
		}
		
		if(clickButton != null && clickButton.equals("Submit"))
		{
			
			
			Connection connection;
							
				
			List<String> errList = new LinkedList<String>();
			

			if(password.equals(""))
			{
				errList.add("Password cannot be empty!");
			}
			
			if(!errList.isEmpty()) { //has some error
				
				
				request.setAttribute("errlist", errList);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("setUpProfessorPassword.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
				
			try {	
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				Statement statement = connection.createStatement();
				String updatePasswordSql = "UPDATE Professor P \n"
						+ "Set P.pw = '" + password + "' \n"
						+ "WHERE P.user_id = '" + id + "'" +";";
						
				
				statement.executeUpdate(updatePasswordSql);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
				requestDispatcher.include(request, response);
				connection.close();
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
	}

}
