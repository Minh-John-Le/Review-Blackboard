package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Beans.StudentUser;

public class StudentDAO {
	
	public boolean DoesStudentSuccessLogin(String email, String password)
	{
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement statement = connection.createStatement();
			String searchStudentsql = "SELECT * \n"
					+ "FROM Student\n"
					+ "WHERE email = '" + email + "' and password ='" + password +"'"
					+";";
			ResultSet studentSearchResult = statement.executeQuery(searchStudentsql);
			
			if (studentSearchResult.next())
			{
				return true;
			}
			connection.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public StudentUser getStudentUSer(String email, String password)
	{
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement statement = connection.createStatement();
			
			
			String searchStudentsql = "SELECT * \n"
					+ "FROM Student\n"
					+ "WHERE email = '" + email + "' and password ='" + password +"'"
					+";";
			ResultSet studentSearchResult = statement.executeQuery(searchStudentsql);
			
			if (studentSearchResult.next())
			{
				int id = studentSearchResult.getInt("user_id");
				String passwordString = studentSearchResult.getString("password");
				String emailString = studentSearchResult.getString("email");
				String fNameString = studentSearchResult.getString("fname");
				String lNameString = studentSearchResult.getString("lname");
				String majorString = studentSearchResult.getString("major");
				

				StudentUser currentStudentUser = new StudentUser(id, passwordString, emailString,fNameString,lNameString,majorString);
				

				connection.close();
				return currentStudentUser;	
			}
			connection.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return null;
	}

	public boolean DoesEmailExist(String email)
	{
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement statement = connection.createStatement();
			
			String searchUsersql = "SELECT P.user_id \n"
					+ "FROM Professor P \n"
					+ "WHERE P.email = '" + email + "'" +""
					+ "UNION \n"
					+ "SELECT S.user_id \n"
					+ "FROM Student S \n"
					+ "WHERE S.email = '" + email + "';";
		
		
			ResultSet resultSet = statement.executeQuery(searchUsersql);
			
			if (resultSet.next())
			{
				return true;
			}
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	
	public void AddStudent(String password, String email, String fName, String lName, String major)
	{
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
					
			String signupUser = "INSERT INTO STUDENT (password, email, fname, lname, major)"
					+ "VALUES(?,?,?,?,?);";
			
			PreparedStatement signupUserPstmt = connection.prepareStatement(signupUser);
			
			signupUserPstmt.setString(1, password);
			signupUserPstmt.setString(2, email);
			signupUserPstmt.setString(3, fName);
			signupUserPstmt.setString(4, lName);
			signupUserPstmt.setString(5, major);
			
			signupUserPstmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
