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
import javax.servlet.http.HttpSession;

import Beans.Professor;
import DAO.ProfessorDAO;

/**
 * Servlet implementation class setUpProfessorPasswordServlet
 */
@WebServlet("/setUpProfessorPasswordServlet")
public class SetUpProfessorPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorDAO professorDAO = new ProfessorDAO();

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
			
			// update professor password
			professorDAO.UpdateProfessorPassword(password, id);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.include(request, response);
		
			return;
			
		}
	}

}
