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

/**
 * Servlet implementation class CreateSchoolServlet
 */
@WebServlet("/addSchoolServlet")
public class CreateSchoolServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();	
		
		
		
	}
    
  	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sName =request.getParameter("sname");
		String street =request.getParameter("street");
		String city =request.getParameter("city");
		String state =request.getParameter("state");
		String zipcode =request.getParameter("zipcode");
		String clickButton = request.getParameter("Button");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(clickButton != null)
        {
        	if(clickButton.equals("Cancel"))
        	{
        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
    			requestDispatcher.forward(request, response);
    			try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			return;
        	}
        }
		List<String> errList = new LinkedList<String>();
		if(sName.equals(""))
		{
			errList.add("School Name cannot be empty!");
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//connection = DriverManager.getConnection("jdbc:mysql://localhost/ReviewBlackboardDB", "dbuser", "dbpassword");
			Statement statement = connection.createStatement();
			String searchSchoolsql = "SELECT * "
					+ "FROM School "
					+ "WHERE sName = '" + sName + "';";
			ResultSet resultSet = statement.executeQuery(searchSchoolsql);
			if(resultSet.next())
			{
				errList.add("School already exist!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("addSchool.jsp");
			requestDispatcher.forward(request, response);
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
		    String addschoolsql = "Insert Into school (sname, street, city, state, zipcode)\n"+ "VALUES(?,?,?,?,?);";
		    PreparedStatement addschoolPstmt = connection.prepareStatement(addschoolsql);
		    addschoolPstmt.setString(1, sName);
		    addschoolPstmt.setString(2, street);
		    addschoolPstmt.setString(3, city);
		    addschoolPstmt.setString(4, state);
		    addschoolPstmt.setString(5, zipcode);
		    addschoolPstmt.executeUpdate();
		    RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
			requestDispatcher.forward(request, response);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

}