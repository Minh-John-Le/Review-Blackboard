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
import DAO.ProfessorDAO;
import DAO.StudentDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context;
	StudentDAO studentDAO = new StudentDAO();
	ProfessorDAO professorDAO = new ProfessorDAO();

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

			
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homeServlet");
		List<String> errList = new LinkedList<String>();
		
		if (password.equals(""))
		{
			errList.add("Password cannot be empty!");
		}
		
		if (studentDAO.DoesStudentSuccessLogin(email, password))
		{
			session.setAttribute("userRole", "student");
		}
		else if (professorDAO.DoesProfessorSuccessLogin(email, password))
		{
			session.setAttribute("userRole", "professor");
		}
		else
		{
			errList.add("Username or Password is incorrect!");
		} 
		
		
		if(!errList.isEmpty()) // no error prceed to home page
		{
			request.setAttribute("errlist", errList);
			requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.include(request, response);
		
			return;
		}
			
		if(session.getAttribute("userRole").equals("student"))
		{

			StudentUser currentStudentUser = studentDAO.getStudentUSer(email, password);
			String fNameString = currentStudentUser.getfName();
			String lNameString = currentStudentUser.getlName();
			
			session.setAttribute("currentStudentUser", currentStudentUser);
			session.setAttribute("currentProfessorUser", null);
			session.setAttribute("name", fNameString + " " + lNameString);
			session.setAttribute("userId", currentStudentUser.getId());
			
			requestDispatcher = request.getRequestDispatcher("homePage.jsp");
			requestDispatcher.forward(request, response);
		
			return;
		}
		
		if(session.getAttribute("userRole").equals("professor"))
		{
		
			
			ProfessorUser currentProfessorUser = professorDAO.getProfessorUser(email, password);
			
			session.setAttribute("currentStudentUser", null);
			session.setAttribute("currentProfessorUser", currentProfessorUser);
			
			String fNameString = currentProfessorUser.getfName();
			String lNameString = currentProfessorUser.getlName();
			session.setAttribute("name", fNameString + " " + lNameString);
			session.setAttribute("userId", currentProfessorUser.getId());
			
			requestDispatcher = request.getRequestDispatcher("homePage.jsp");
			requestDispatcher.forward(request, response);
	
			return;
		}
	}
	

}