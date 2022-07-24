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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/reportProfessorReviewServlet")
public class ReportProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
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
		
		System.out.println(textCont);
		
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
			
			reportReview(currentReport,studentID,textCont);		
		}
		
		
		
		
			
		Professor selectedProfessor = (Professor) session.getAttribute("selectedProfessor");
		String Id = String.valueOf(selectedProfessor.getUser_ID());
		double avgD = this.AvgProfessorDifficulty(Id);
		double avgQ = this.AvgProfessorQuality(Id);
		
		selectedProfessor.setAvgDifficulty(avgD);
		selectedProfessor.setAvgQuality(avgQ);
		
		session.setAttribute("selectedProfessor", selectedProfessor);
		LinkedList<ProfessorReview> reviewList = this.ProfessorReviewList(Id);
		System.out.println(reviewList.size());
		session.setAttribute("professorReview", reviewList);
		
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
		requestDispatcher.forward(request, response);
		
		return;
		
	}

	private void reportReview(String reviewID, String author, String content)
	{
		try {
			
			LocalDateTime today = LocalDateTime.now();
			String todayString = today.toString();
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			//Statement statement = connection.createStatement();
			String insertReportsql = "INSERT INTO Stud_reports_Prev(sid, prid, text_cont, report_date) \n"
					+ "VALUES(?,?,?,?);";
			
			PreparedStatement insertReportStmt = connection.prepareStatement(insertReportsql);
			
			insertReportStmt.setString(1, reviewID);
			insertReportStmt.setString(2, author);
			insertReportStmt.setString(3, content);
			insertReportStmt.setString(4, todayString);
			

			insertReportStmt.executeUpdate();
				
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private LinkedList<ProfessorReview> ProfessorReviewList(String professorID)
	{
		LinkedList<ProfessorReview> reviewList= new LinkedList<ProfessorReview>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String searchProfessorReviewsql = "SELECT * \r\n"
					+ "FROM reviewblackboarddb.Prof_Reviews PR\r\n"
					+ "LEFT JOIN reviewblackboarddb.Comm_prof_rev C\r\n"
					+ "ON  PR.prid = C.prid\r\n"
					+ "ORDER BY PR.pub_date DESC;";


			ResultSet searchResult = statement.executeQuery(searchProfessorReviewsql);
			
		
			
			while(searchResult.next())
			{
				int  reviewIDString = searchResult.getInt("prid");
				String contentString = searchResult.getString("text_cont");
				int profID = searchResult.getInt("prof");
				int quality = searchResult.getInt("quality");
				int difficulty = searchResult.getInt("difficulty");
				String course_name = searchResult.getString("course_name");
				String class_type = searchResult.getString("class_type");
				String grade = searchResult.getString("grade");
				String year = searchResult.getString("Pyear");
				String semester = searchResult.getString("semester");
				String comment = searchResult.getString("C.text_cont");
				
				if (comment == null)
				{
					comment = "";
				}
				
				ProfessorReview review = new ProfessorReview(reviewIDString, contentString, profID, quality, difficulty, course_name, 
						class_type,grade, year, semester,comment);
				reviewList.add(review);
			}
			
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewList;
		
	}
	
	
	private double AvgProfessorDifficulty(String professorID)
	{
		double result = -1.0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String avgDifficultyReviewsql = "SELECT AVG(PR.Difficulty) As avgD "
					+ "FROM Prof_Reviews PR\r\n"
					+ "WHERE PR.Prof = '" + professorID + "';";


			ResultSet searchResult = statement.executeQuery(avgDifficultyReviewsql);
			if(searchResult.next())
			{
				 result= searchResult.getDouble(1);
			}
			result = (double) (Math.round(result*100)/100);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return result;
	}
	
	
	private double AvgProfessorQuality(String professorID)
	{
		double result = -1.0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String avgQualityReviewsql = "SELECT AVG(PR.Quality) As avgQ\r\n"
					+ "FROM Prof_Reviews PR\r\n"
					+ "WHERE PR.Prof = '" + professorID + "';";


			ResultSet searchResult = statement.executeQuery(avgQualityReviewsql);
			if(searchResult.next())
			{
				 result= searchResult.getDouble(1);
			}
			result = (double) (Math.round(result*100)/100);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return result;
	}

}
