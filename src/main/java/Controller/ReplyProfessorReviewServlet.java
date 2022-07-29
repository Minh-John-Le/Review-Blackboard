package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import Beans.ProfessorUser;
import Beans.StudentUser;
import DAO.ProfessorReviewDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/replyProfessorReviewServlet")
public class ReplyProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReivewDAO();
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		String currentReport = (String) session.getAttribute("currentReport");
		String textCont = request.getParameter("textContent").trim();
		ProfessorUser professorUser = (ProfessorUser) session.getAttribute("currentProfessorUser"); 
		String studentID = String.valueOf(professorUser.getId());
		
		Professor professor = (Professor) session.getAttribute("selectedProfessor");
		String professorID = String.valueOf(professor.getUser_ID());
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
		

		if (clickButton.equals("Submit"))
		{
			if(!errlist.isEmpty()) // no error prceed to home page
			{
				request.setAttribute("errlist", errlist);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("replyProfessorReview.jsp");
				requestDispatcher.include(request, response);
				return;
			}
			
			professorReviewDAO.replyReview(currentReport,professorID,textCont);		
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
