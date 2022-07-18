package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PreInitServlet
 */
@WebServlet(urlPatterns = "/preInitServlet", loadOnStartup = 0)


public class PreInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;
    
    
	public void init(ServletConfig config)
	{		
			try {
				ServletContext context = config.getServletContext();
				System.out.println("init()");
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(context.getInitParameter("hostUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			
				java.sql.Statement statement = connection.createStatement();
				String databaseCreation = "CREATE DATABASE  IF NOT EXISTS ReviewBlackboardDB;";
				
				statement.executeUpdate(databaseCreation);
				connection.close();
				
				//-----------------------------
				
				connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				
				statement = connection.createStatement();
				String usertableDDL = "CREATE TABLE IF NOT EXISTS STUDENT_USER("
						+ "Email VARCHAR(50) NOT NULL,"
						+ "DisplayName VARCHAR(50) NOT NULL,"
						+ "Password VARCHAR(50) NOT NULL,"
						+ "PRIMARY KEY(Email)"
						+ ");";
				statement.executeUpdate(usertableDDL);
				
				
				String reviewDDL = " CREATE TABLE IF NOT EXISTS REVIEWS("
						+ "Student VARCHAR(50) NOT NULL,"
						+ "Quality Integer NOT NULL,"
						+ "Difficulty Integer NOT NULL,"
						+ "CourseName VARCHAR(50) NOT NULL,"
						+ "Professor VARCHAR(50) NOT NULL,"
						+ "School VARCHAR(50) NOT NULL,"
						+ "Zip Integer NOT NULL,"
						+ "Content VARCHAR(1000) NOT NULL"
						+ ");";
				statement.executeUpdate(reviewDDL);
				
				connection.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
