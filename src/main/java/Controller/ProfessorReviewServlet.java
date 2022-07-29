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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/professorReviewServlet")
public class ProfessorReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parameter
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();
		
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
			
			
			
			double avgD = this.AvgProfessorDifficulty(professorID);
			double avgQ = this.AvgProfessorQuality(professorID);
			
			selectedProfessor.setAvgDifficulty(avgD);
			selectedProfessor.setAvgQuality(avgQ);
			
			session.setAttribute("selectedProfessor", selectedProfessor);
			LinkedList<ProfessorReview> reviewList = this.ProfessorReviewList(professorID, course, fromYear,  toYear, quality, difficulty,  classStyle ,grade);
			session.setAttribute("professorReview", reviewList);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
			requestDispatcher.forward(request, response);
			return;
		}
		
		
	}
	
	private LinkedList<ProfessorReview> ProfessorReviewList(String professorID, String course, 
			String fYear, String toYear, 
			String quality, String difficulty, String classStyle, String grade)
	{
		LinkedList<ProfessorReview> reviewList= new LinkedList<ProfessorReview>();
		if(fYear == null || fYear.equals(""))
		{
			fYear = "'0000'";
		}
		
		if(toYear == null || toYear.equals(""))
		{
			toYear = "'9999'";
		}
		
		if(course == null || course.equals(""))
		{
			course = "";
		}
		
		if(quality == null || quality.length() > 2)
		{
			quality = "LIKE '%' ";
		}
		else
		{
			quality = "= '" + quality +"' "; 
		}
		
		if(difficulty == null || difficulty.length() > 2)
		{
			difficulty = "LIKE '%' ";
		}
		else
		{
			difficulty = "= '" + difficulty +"' "; 
		}
		
		if(classStyle == null || classStyle.equals("---Class Style---"))
		{
			classStyle = "LIKE '%' ";
		}
		else
		{
			classStyle = "= '" + classStyle +"' "; 
		}
		
		if(grade.equals("---Grade---"))
		{
			grade = "Like '%'";
		}
		else
		{
			grade = "= '" + grade +"' ";
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String searchProfessorReviewsql = "SELECT distinct *\r\n"
					+ "FROM Prof_Reviews PR\r\n"
					+ "LEFT JOIN Comm_prof_rev C \r\n"
					+ "ON  PR.prid = C.prid \n"
					+ "WHERE prof = '" + professorID + "' AND \n"
							+ "quality " + quality + " AND \n"
							+ "difficulty " + difficulty + " AND \n"
							+ "course_name LIKE '" + course +"%' AND \n"
							+ "class_type " + classStyle + " AND \n"
							+ "grade " + grade + " AND \n"
							+ "Pyear > " + fYear + " AND Pyear < " + toYear +"\n"
					+ "ORDER BY PR.pub_date DESC;";

			ResultSet searchResult = statement.executeQuery(searchProfessorReviewsql);
			
		
			
			while(searchResult.next())
			{
				int  reviewIDString = searchResult.getInt("prid");
				String contentString = searchResult.getString("text_cont");
				int profID = searchResult.getInt("prof");
				int qualityString = searchResult.getInt("quality");
				int difficultyString = searchResult.getInt("difficulty");
				String course_name = searchResult.getString("course_name");
				String class_type = searchResult.getString("class_type");
				String gradeString = searchResult.getString("grade");
				String year = searchResult.getString("Pyear");
				String semester = searchResult.getString("semester");
				String comment = searchResult.getString("C.text_cont");
				
				if (comment == null)
				{
					comment = "";
				}
				
				ProfessorReview review = new ProfessorReview(reviewIDString, contentString, profID, qualityString, difficultyString, course_name, 
						class_type,gradeString, year, semester,comment);
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
