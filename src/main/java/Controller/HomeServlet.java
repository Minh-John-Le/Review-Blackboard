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
import Beans.School;
import DAO.ProfessorDAO;
import DAO.SchoolDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/homeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorDAO professorDAO = new ProfessorDAO();
	private SchoolDAO schoolDAO = new SchoolDAO();
	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parametter
		String clickButton = request.getParameter("Button");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String school = request.getParameter("school");
		LinkedList<Professor> professorList = new LinkedList<Professor>();
		
		HttpSession session = request.getSession();
		
		
		// Go to Sign up page when click "Sign Up"
		
		if (clickButton != null)
		{
			// if click a button then process
			if (clickButton.equals("Review Blackboard"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			if (clickButton.equals("Log Out"))
			{
				session.setAttribute("currentStudentUser", null);
				session.setAttribute("currentProfessorUser", null);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			if (clickButton.equals("Add Professor"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddProfessor.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			if (clickButton.equals("Add School"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("addSchool.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			if (clickButton.equals("Support"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("supportTicket.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
		
		}

				
		if (clickButton.equals("Search School")) {
			
			LinkedList<School> searchSchool = schoolDAO.SearchSchool(school);
			
			if(!searchSchool.isEmpty())
			{
				session.setAttribute("searchSchoolList", searchSchool);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("schoolSearchResult.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
			requestDispatcher.include(request, response);
			return;
			
		}
		
		else if(clickButton.equals("Search Professor"))
		{
			professorList = professorDAO.SearchProfessor(fname, lname, school);
			
			if (!professorList.isEmpty())
			{
				session.setAttribute("searchProfessorList", professorList);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorSearchResult.jsp");
				requestDispatcher.include(request, response);
				
				return;
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");
			requestDispatcher.include(request, response);
			
			
		}	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	
}
