package Controller;

import java.io.IOException;
import java.util.LinkedList;

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
 * Servlet implementation class SearchProfessorForSignUp
 */
@WebServlet("/searchProfessorForSignUpServlet")
public class SearchProfessorForSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProfessorDAO professorDAO = new ProfessorDAO();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get all parametter
			String clickButton = request.getParameter("Button");
			String fname = request.getParameter("fname").trim();
			String lname = request.getParameter("lname").trim();
			String school = request.getParameter("school").trim();
			
			LinkedList<Professor> professorList = new LinkedList<Professor>();
			
			HttpSession session = request.getSession();
					
			if(clickButton != null && clickButton.equals("Submit"))
			{
				
				professorList = professorDAO.SearchProfessorForSignUp(fname, lname, school);
				
				if (professorList.size() > 0)
				{
					session.setAttribute("searchProfessorList", professorList);
				}
				else
				{
					session.setAttribute("searchProfessorList", null);
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
				requestDispatcher.include(request, response);
				return;
				
	
			}
			
			
			
			if(clickButton != null && clickButton.equals("Cancel"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
				requestDispatcher.include(request, response);
				return;
			}
			
			
			if(clickButton != null && clickButton.equals("Add myself"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("signUpAsProfessor.jsp");
				requestDispatcher.include(request, response);
			
				return;
			}
				
			
			
			@SuppressWarnings("unchecked")
			LinkedList<Professor> currentProfessorList = (LinkedList<Professor>) (session.getAttribute("searchProfessorList"));
			
			int index = 0;
			while(index < currentProfessorList.size())
			{
				String Id = String.valueOf(currentProfessorList.get(index).getUser_ID());
				String select = "select" + Id;
				
				if(request.getParameter(select) != null)
				{

					session.setAttribute("selectedProfessor", currentProfessorList.get(index));									
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("setUpProfessorPassword.jsp");		
					requestDispatcher.forward(request, response);
					
					return;
							
				}
				index++;			
			}
					
	}

}
