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
 * Servlet implementation class AddProfessor
 */
@WebServlet("/AddProfessorServlet")
public class AddProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();	
		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pw = "";
		String fname =request.getParameter("fname");
		String lname =request.getParameter("lname");
		String email =request.getParameter("email");
		String department =request.getParameter("department");
		String schoolName =request.getParameter("schoolName");
		String clickButton = request.getParameter("Button");
		
        List<String> errList = new LinkedList<String>();
        
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
        
		if(email.equals(""))
		{
			errList.add("Email cannot be empty!");
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//connection = DriverManager.getConnection("jdbc:mysql://localhost/ReviewBlackboardDB", "dbuser", "dbpassword");
			Statement statement = connection.createStatement();
			String addprofessorsql = "SELECT * FROM professor \n"+
					"Where email = '" +  email + "';";
			ResultSet resultset = statement.executeQuery(addprofessorsql);
			if(resultset.next())
			{
				errList.add("Professor already exist!");
		
			}
			
		} catch (SQLException e3) {
			e3.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddProfessor.jsp");
			requestDispatcher.forward(request, response);
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
			String addprofessorsql = "Insert Into Professor (pw, email, fname, lname, schoolName, department)\n"+ "VALUES(?,?,?,?,?,?);";
			 PreparedStatement addProfessorPstmt = connection.prepareStatement(addprofessorsql);
			    addProfessorPstmt.setString(1, pw);
			    addProfessorPstmt.setString(2, email);
			    addProfessorPstmt.setString(3, fname);
			    addProfessorPstmt.setString(4, lname);
			    addProfessorPstmt.setString(5, schoolName);
			    addProfessorPstmt.setString(6, department);
			    addProfessorPstmt.executeUpdate();
			    RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
				requestDispatcher.forward(request, response);
				connection.close();
				
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
}


