package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SupportTicketDAO {
	
	public void AddStudentSupportTicket(String studentID, String supportTag, String content)
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
			
			//Statement statement = connection.createStatement();
			String insertReportsql = "INSERT INTO StudentSupportTicket(StudentID, supportTag, Text_content, publishDate) \n"
					+ "VALUES(?,?,?,?);";
			
			PreparedStatement insertReportStmt = connection.prepareStatement(insertReportsql);
			
			insertReportStmt.setString(1, studentID);
			insertReportStmt.setString(2, supportTag);
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
	
	

	public void AddProfessorSupportTicket(String professorID, String supportTag, String content)
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
			
			//Statement statement = connection.createStatement();
			String insertReportsql = "INSERT INTO ProfessorSupportTicket(ProfessorID, supportTag, Text_content, publishDate) \n"
					+ "VALUES(?,?,?,?);";
			
			PreparedStatement insertReportStmt = connection.prepareStatement(insertReportsql);
			
			insertReportStmt.setString(1, professorID);
			insertReportStmt.setString(2, supportTag);
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
}
