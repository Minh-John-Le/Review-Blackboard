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
import Beans.ProfessorReview;
import DAO.ProfessorReviewDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/professorReviewServlet")
public class ProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private ServletContext context;
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReviewDAO();

		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parameter
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		LinkedList<ProfessorReview> proReviewList = (LinkedList<ProfessorReview>) (session.getAttribute("professorReview"));
		
	// Check is user click any report button
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
		
		if(session.getAttribute("userRole").equals("student"))
		{
			int index = 0;
			while(index < proReviewList.size())
			{
				String Id = String.valueOf(proReviewList.get(index).getReviewId());
				String report = "report" + Id;
				
				if(request.getParameter(report) != null)
				{
					
					session.setAttribute("currentReport", Id);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("reportProfessorReview.jsp");		
					requestDispatcher.forward(request, response);
					
					return;
					
								
				}
				index++;			
			}
		}
		
		if(session.getAttribute("userRole").equals("professor"))
		{
			int index = 0;
			while(index < proReviewList.size())
			{
				String Id = String.valueOf(proReviewList.get(index).getReviewId());
				String reply = "reply" + Id;
				
				if(request.getParameter(reply) != null)
				{
					
					session.setAttribute("currentReport", Id);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("replyProfessorReview.jsp");		
					requestDispatcher.forward(request, response);
					
					return;
												
				}
				index++;			
			}
		}
		
		if (clickButton != null)
		{
			// if click a button then process
			if (clickButton.equals("Write Review"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("writeProfessorReview.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
		}
		
		
		if (clickButton.equals("Submit"))
		{
			Professor selectedProfessor = (Professor) session.getAttribute("selectedProfessor");
			String course = request.getParameter("course");
			String fromYear = request.getParameter("fromYear");
			String toYear = request.getParameter("toYear");

			String quality = request.getParameter("quality");
			String difficulty = request.getParameter("difficulty");
			String classStyle = request.getParameter("classStyle");
			String grade = request.getParameter("grade");	
			String professorID = String.valueOf(selectedProfessor.getUser_ID());
			
			
			
			double avgD = professorReviewDAO.AvgProfessorDifficulty(professorID);
			double avgQ = professorReviewDAO.AvgProfessorQuality(professorID);
			
			selectedProfessor.setAvgDifficulty(avgD);
			selectedProfessor.setAvgQuality(avgQ);
			
			session.setAttribute("selectedProfessor", selectedProfessor);
			LinkedList<ProfessorReview> reviewList = professorReviewDAO.ProfessorReviewList(professorID, course, fromYear,  toYear, quality, difficulty,  classStyle ,grade);
			session.setAttribute("professorReview", reviewList);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
			requestDispatcher.forward(request, response);
			return;
		}
		
		
	}
	
	
}
