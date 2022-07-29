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
import Beans.StudentUser;
import DAO.ProfessorReviewDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/reportProfessorReviewServlet")
public class ReportProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReviewDAO();

		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		String currentReport = (String) session.getAttribute("currentReport");
		String textCont = request.getParameter("textContent").trim();
		StudentUser studentUser = (StudentUser) session.getAttribute("currentStudentUser"); 
		String studentID = String.valueOf(studentUser.getId());
		
		Professor professor = (Professor) session.getAttribute("selectedProfessor");
		LinkedList<String> errlist = new LinkedList<String>();
		
		
		if(clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
			requestDispatcher.forward(request, response);	

		}
				
		if (textCont == null || textCont.equals(""))
		{
			errlist.add("Report content cannot be empty!");
		}
		
		
		if (studentUser == null || professor == null)
		{
			return;
	
		}
		
		if (clickButton.equals("Submit"))
		{
			if(!errlist.isEmpty()) // no error prceed to home page
			{
				request.setAttribute("errlist", errlist);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("reportProfessorReview.jsp");
				requestDispatcher.include(request, response);
				return;
			}
			
			professorReviewDAO.reportReview(currentReport,studentID,textCont);		
		}
			
		Professor selectedProfessor = (Professor) session.getAttribute("selectedProfessor");
		String Id = String.valueOf(selectedProfessor.getUser_ID());
		double avgD = professorReviewDAO.AvgProfessorDifficulty(Id);
		double avgQ = professorReviewDAO.AvgProfessorQuality(Id);
		
		selectedProfessor.setAvgDifficulty(avgD);
		selectedProfessor.setAvgQuality(avgQ);
		
		session.setAttribute("selectedProfessor", selectedProfessor);
		LinkedList<ProfessorReview> reviewList = professorReviewDAO.ProfessorReviewList(Id);
		session.setAttribute("professorReview", reviewList);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
		requestDispatcher.forward(request, response);
		
		return;
		
	}


}	

