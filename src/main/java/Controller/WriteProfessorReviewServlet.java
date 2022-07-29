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
import Beans.StudentUser;
import DAO.ProfessorReviewDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/writeProfessorReviewServlet")
public class WriteProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReviewDAO();
	
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		
		StudentUser studentUser = (StudentUser) session.getAttribute("currentStudentUser"); 
		Professor professor = (Professor) session.getAttribute("selectedProfessor");
		
		if (studentUser == null || professor == null)
		{
			return;
		}
		
		if(clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
			requestDispatcher.forward(request, response);
			return;

		}
		
		String course = request.getParameter("course").trim();
		String year = request.getParameter("year").trim();
		String semester = request.getParameter("semester").trim();
		String quality = request.getParameter("quality").trim();
		String difficulty = request.getParameter("difficulty").trim();
		String classStyle = request.getParameter("classStyle").trim();
		String grade = request.getParameter("grade").trim();	
		String textContent = request.getParameter("textContent").trim();
		int userID = studentUser.getId();
		int professorID = professor.getUser_ID();
		

		LinkedList<String> errlist = new LinkedList<String>();
		
		if(course == null || year.equals(""))
		{
			errlist.add("course cannot be empty!");
		}
		
		
		if(year == null || year.equals(""))
		{
			errlist.add("year cannot be empty!");
		}
		
		if(quality.length() > 2)
		{
			errlist.add("Please select quality!");
		}
			
		
		if(difficulty.length() > 2)
		{
			errlist.add("Please select difficulty!");
		}
		
		if(semester.length() > 7)
		{
			errlist.add("Please select Semester/Quarter!");
		}
		
		if(classStyle.length() > 10)
		{
			errlist.add("Please select classStyle!");
		}
		
		if(grade.equals("---Grade---"))
		{
			errlist.add("Please select grade!");
		}
		
		if(textContent.equals(""))
		{
			errlist.add("Please enter some review!");
		}
		
		
		if(!errlist.isEmpty()) // no error prceed to home page
		{
			request.setAttribute("errlist", errlist);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("writeProfessorReview.jsp");
			requestDispatcher.include(request, response);
			return;
		}
		
	
		professorReviewDAO.AddProfessorReview(userID, professorID, textContent, quality, difficulty,
				course, classStyle, grade, year, semester);
		
				
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
