package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Professor;
import Beans.ProfessorReview;
import DAO.ProfessorReviewDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/professorSearchResultServlet")
public class ProfessorSearchResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReviewDAO();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parameter
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		LinkedList<Professor> professorList = (LinkedList<Professor>) (session.getAttribute("searchProfessorList"));
	
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
		}
		
		
		// Check is user click any view button
		int index = 0;
		while(index < professorList.size())
		{
			String Id = String.valueOf(professorList.get(index).getUser_ID());
			String view = "view" + Id;
			
			if(request.getParameter(view) != null)
			{
				Double avgD = professorReviewDAO.AvgProfessorDifficulty(Id);
				Double avgQ = professorReviewDAO.AvgProfessorQuality(Id);
				
				professorList.get(index).setAvgDifficulty(avgD);
				professorList.get(index).setAvgQuality(avgQ);
				session.setAttribute("selectedProfessor", professorList.get(index));
				LinkedList<ProfessorReview> reviewList = professorReviewDAO.ProfessorReviewList(Id);
				session.setAttribute("professorReview", reviewList);
				
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
				requestDispatcher.forward(request, response);
				
				return;
				
							
			}
			index++;			
		}
		
	}

}
