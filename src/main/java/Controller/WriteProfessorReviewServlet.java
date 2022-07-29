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
	private ServletContext context;
	private ProfessorReviewDAO professorReviewDAO = new ProfessorReviewDAO();
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
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
		LocalDateTime today = LocalDateTime.now();
		String todayString = today.toString();

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
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String searchReviewsql = "SELECT COUNT(P.author)\r\n"
					+ "FROM Prof_Reviews P \n"
					+ "WHERE P.author = '" + userID + "' \n"
					+ "AND P.prof = '" + professorID + "';";
			
			ResultSet searchResult = statement.executeQuery(searchReviewsql);
			int countResult = 0;
			if(searchResult.next())
			{
				 countResult= searchResult.getInt(1);
			}
			
			if(countResult < 1)
			{
											
				String addReviewsql = "INSERT INTO Prof_Reviews(pub_date,text_cont, author, "
						+ "prof, quality,difficulty, "
						+ "course_name, class_type, grade, Pyear, semester) \n"
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement addReviewPstmt = connection.prepareStatement(addReviewsql);
				
				addReviewPstmt.setString(1, todayString);
				addReviewPstmt.setString(2, textContent);	
				addReviewPstmt.setInt(3, userID);
				addReviewPstmt.setInt(4, professorID);
				addReviewPstmt.setString(5, quality);
				addReviewPstmt.setString(6, difficulty);
				addReviewPstmt.setString(7, course );
				addReviewPstmt.setString(8, classStyle );			
				addReviewPstmt.setString(9, grade );
				addReviewPstmt.setString(10, year );
				addReviewPstmt.setString(11, semester);
				
				addReviewPstmt.executeUpdate();
				
			}
			
			else
			{

				String addReviewsql = "UPDATE Prof_Reviews PR\n"
						+ "SET PR.pub_date = ? , \n"
						+ "PR.text_cont = ?, \n"
						+ "PR.author = ?, \n "
						+ "PR.prof = ?, \n"
						+ "PR.quality = ?, \n"
						+ "PR.difficulty = ?, \n"
						+ "PR.course_name = ?, \n"
						+ "PR.class_type = ?, \n"
						+ "PR.grade = ?, \n"
						+ "PR.Pyear = ?, \n"
						+ "PR.semester = ? \n"
						+ "WHERE PR.author = '" + userID + "' AND \n"
						+ "PR.prof = '" + professorID + "';";
						
				
				PreparedStatement addReviewPstmt = connection.prepareStatement(addReviewsql);
				
				addReviewPstmt.setString(1, todayString);
				addReviewPstmt.setString(2, textContent);	
				addReviewPstmt.setInt(3, userID);
				addReviewPstmt.setInt(4, professorID);
				addReviewPstmt.setString(5, quality);
				addReviewPstmt.setString(6, difficulty);
				addReviewPstmt.setString(7, course );
				addReviewPstmt.setString(8, classStyle );			
				addReviewPstmt.setString(9, grade );
				addReviewPstmt.setString(10, year );
				addReviewPstmt.setString(11, semester);
				
				addReviewPstmt.executeUpdate();
				
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
			connection.close();
			return;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
