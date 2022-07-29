package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Beans.Professor;
import Beans.ProfessorReview;

public class ProfessorReviewDAO {
	
	public void replyReview(String reviewID, String professor, String content)
	{
		InitialContext initialContext;
		try {
			
			LocalDateTime today = LocalDateTime.now();
			String todayString = today.toString();
			
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			
			Statement statement = connection.createStatement();
			String searchCommentsql = "SELECT distinct *\r\n"
					+ "FROM comm_prof_rev C \n"
					+ "WHERE C.prid = '" + reviewID + "' AND\n"
						+ " C.professor = '" + professor + "';";
			
			ResultSet searchResult = statement.executeQuery(searchCommentsql);
			
			if(!searchResult.next())
			{
											
				String addReviewsql = "INSERT INTO comm_prof_rev(prid, professor, pub_date, text_cont) \n"
						+ "VALUES(?,?,?,?)";
				PreparedStatement addReviewPstmt = connection.prepareStatement(addReviewsql);
				
				addReviewPstmt.setString(1, reviewID);
				addReviewPstmt.setString(2, professor);	
				addReviewPstmt.setString(3, todayString);
				addReviewPstmt.setString(4, content);		
				addReviewPstmt.executeUpdate();
				
			}
			
			else
			{

				String addReviewsql = "UPDATE comm_prof_rev C \n"
						+ "SET C.pub_date = ? , \n"
						+ "C.text_cont = ? \n"
						+ "WHERE C.prid = '" + reviewID + "' AND\n"
						+ " C.professor = '" + professor + "';";

			
						
				
				PreparedStatement addReviewPstmt = connection.prepareStatement(addReviewsql);
				
				addReviewPstmt.setString(1, todayString);
				addReviewPstmt.setString(2, content);	
	
				
				addReviewPstmt.executeUpdate();
				
			}
		
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void reportReview(String reviewID, String author, String content)
	{
		InitialContext initialContext;
		try {
			
			LocalDateTime today = LocalDateTime.now();
			String todayString = today.toString();
			
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	
			/*
			Statement statement = connection.createStatement();
			String searchReportSql = "SELECT * \n"
					+ "FROM Stud_reports_Prev R \n"
					+ "WHERE R.sid ='" + author + "' AND\n"
					+ " prid = '" + reviewID + "';";
			
			ResultSet searchResult = statement.executeQuery(searchReportSql);
			
			if(searchResult.next())
			{
				String updateReport = "UPDATE Stud_reports_Prev \n"
						+ "SET text_cont = '"+ content + "', \n"
						+ "report_date = '" + todayString + "'\n"
						+ "WHERE sid ='" + author + "' AND\n"
						+ "prid = '" + reviewID + "';";
				statement.executeUpdate(updateReport);
				connection.close();
				return;
			}
			*/
			String insertReportsql = "INSERT INTO Stud_reports_Prev(sid, prid, text_cont, report_date) \n"
					+ "VALUES(?,?,?,?);";
			
			PreparedStatement insertReportStmt = connection.prepareStatement(insertReportsql);
			
			insertReportStmt.setString(1, author);
			insertReportStmt.setString(2, reviewID);
			insertReportStmt.setString(3, content);
			insertReportStmt.setString(4, todayString);
			

			insertReportStmt.executeUpdate();
				
			connection.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LinkedList<ProfessorReview> ProfessorReviewList(String professorID, String course, 
			String fYear, String toYear, 
			String quality, String difficulty, String classStyle, String grade)
	{
		
		if(fYear == null || fYear.equals("") || fYear.length() > 4)
		{
			fYear = "'0000'";
		}
		
		if(toYear == null || toYear.equals("") || toYear.length() > 4)
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
		
		
		LinkedList<ProfessorReview> reviewList= new LinkedList<ProfessorReview>();
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewList;
		
	}
	

	
	public LinkedList<ProfessorReview> ProfessorReviewList(String professorID)
	{
		LinkedList<ProfessorReview> reviewList= new LinkedList<ProfessorReview>();
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement statement = connection.createStatement();
			String searchProfessorReviewsql = "SELECT Distinct * \r\n"
					+ "FROM Prof_Reviews PR\r\n"
					+ "LEFT JOIN Comm_prof_rev C\r\n"
					+ "ON  PR.prid = C.prid\r\n"
					+ "WHERE PR.prof = '" + professorID + "'"
					+ "ORDER BY PR.pub_date DESC;";


			ResultSet searchResult = statement.executeQuery(searchProfessorReviewsql);
			
		
			
			while(searchResult.next())
			{
				int  reviewIDString = searchResult.getInt("PR.prid");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewList;
		
	}


	
	public double AvgProfessorDifficulty(String professorID)
	{
		double result = -1.0;
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return result;
	}
	
	
	
	public double AvgProfessorQuality(String professorID)
	{
		double result = -1.0;
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return result;
	}

}
