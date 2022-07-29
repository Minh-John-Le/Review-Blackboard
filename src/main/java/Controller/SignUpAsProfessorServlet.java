package Controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.ProfessorDAO;

@WebServlet("/signUpAsProfessorServlet")
public class SignUpAsProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProfessorDAO professorDAO = new ProfessorDAO();

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String school = request.getParameter("school");
		String department = request.getParameter("department");
		

		//===================

		String clickButton = request.getParameter("Button");
	
	
		// Return to Log In page if click cancel
		if(clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
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
		
		
		if(professorDAO.DoesEmailExist(email))
		{
			errList.add("Username already exist!");
		}
		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("signUpAsProfessor.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
			
		// add professor
			professorDAO.AddProfessor(password, email, fName, lName, school, department);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
				
			requestDispatcher.forward(request, response);

	}
	


}
