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

import DAO.StudentDAO;

@WebServlet("/signUpAsStudentServlet")
public class SignUpAsStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO studentDAO = new StudentDAO();
	ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String major = request.getParameter("major");
		

		//===================
	
			
		String clickButton = request.getParameter("Button");
	
	
		// Return to Log In page if click cancel
		if(clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		
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
		
		
		if(studentDAO.DoesEmailExist(email))
		{
			errList.add("Username already exist!");
		}
		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpAsStudent.jsp");
			requestDispatcher.forward(request, response);
	
			return;
		}
	
		//  Happy Flow success create new user
		studentDAO.AddStudent(password, email, fName, lName, major);
	
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
	
		requestDispatcher.forward(request, response);
	}
	


}
