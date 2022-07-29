package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProfessorDAO;

/**
 * Servlet implementation class AddProfessor
 */
@WebServlet("/AddProfessorServlet")
public class AddProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorDAO professorDAO = new ProfessorDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pw = "";
		String fname =request.getParameter("fname");
		String lname =request.getParameter("lname");
		String email =request.getParameter("email");
		String department =request.getParameter("department");
		String schoolName =request.getParameter("schoolName");
		String clickButton = request.getParameter("Button");
		
        List<String> errList = new LinkedList<String>();

        
    	if(clickButton != null)
        {
        	if(clickButton.equals("Cancel"))
        	{
        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
    			requestDispatcher.forward(request, response);
    			return;
        	}
        }
        
		if(email.equals(""))
		{
			errList.add("Email cannot be empty!");
		}
		
	
		//connection = DriverManager.getConnection("jdbc:mysql://localhost/ReviewBlackboardDB", "dbuser", "dbpassword");
		
		if(professorDAO.doesProfessorExist(email))
		{
			errList.add("Professor already exist!");
	
		}
		
		
		
		if(!errList.isEmpty()) { //has some error
			
			request.setAttribute("errlist", errList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddProfessor.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		professorDAO.AddProfessor(pw, email, fname, lname, schoolName, department);
			 
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
		requestDispatcher.forward(request, response);
	
	}
	
}


