package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;

import Beans.Professor;
import Beans.ProfessorUser;
import Beans.StudentUser;

public class ProfessorDAO {
	public void AddProfessor(String pw, String email, String fname, String lname, String schoolName, String department)
	{
		InitialContext initialContext;
		
		
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			
			String addprofessorsql = "Insert Into Professor (pw, email, fname, lname, schoolName, department)\n"+ "VALUES(?,?,?,?,?,?);";
			PreparedStatement addProfessorPstmt = connection.prepareStatement(addprofessorsql);
			    addProfessorPstmt.setString(1, pw);
			    addProfessorPstmt.setString(2, email);
			    addProfessorPstmt.setString(3, fname);
			    addProfessorPstmt.setString(4, lname);
			    addProfessorPstmt.setString(5, schoolName);
			    addProfessorPstmt.setString(6, department);
			    addProfessorPstmt.executeUpdate();
			    
				connection.close();
				
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean doesProfessorExist(String email)
	{
		
		InitialContext initialContext;
		
		
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement statement = connection.createStatement();
			String addprofessorsql = "SELECT * FROM professor \n"+
					"Where email = '" +  email + "';";
			ResultSet resultset = statement.executeQuery(addprofessorsql);
			
			if(resultset.next())
			{
				connection.close();
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
	
	
	public LinkedList<Professor> SearchProfessor(String fname, String lname, String school)
	{
		InitialContext initialContext;
		LinkedList<Professor> professorList = new LinkedList<Professor>();
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			
			
			Statement statement = connection.createStatement();
			String searchProfessorsql = "SELECT *\r\n"
					+ "FROM professor P\r\n"
					+ "WHERE P.fname LIKE '" + fname +"%' \r\n"
					+ "AND P.lname LIKE '" + lname +"%' \r\n"
					+ "AND P.schoolName Like '" + school + "%';";	
			
			ResultSet ProfessorSearchResult = statement.executeQuery(searchProfessorsql);

			
			
			while(ProfessorSearchResult.next())
			{
				int user_IdString = ProfessorSearchResult.getInt("user_ID");
				String fnameString =  ProfessorSearchResult.getString("fname");
				String lnameString =  ProfessorSearchResult.getString("lname");
				String schoolString = ProfessorSearchResult.getString("schoolName");
				String emailString = ProfessorSearchResult.getString("email");
				Professor professor = new Professor(user_IdString, fnameString, lnameString, schoolString,emailString);
				professorList.add(professor);	
			}
			
			connection.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return professorList;
		
	}


	public boolean DoesProfessorSuccessLogin(String email, String password)
	{
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement statement = connection.createStatement();
			String searchProfessorsql = "SELECT * \n"
					+ "FROM Professor\n"
					+ "WHERE email = '" + email + "' and pw ='" + password +"'"
					+";";
			ResultSet searchProfessorResult = statement.executeQuery(searchProfessorsql);
			
			if (searchProfessorResult.next())
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

	public ProfessorUser getProfessorUser(String email, String password)
	{
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);			
			Statement statement = connection.createStatement();
			
			String searchProfessorsql = "SELECT * \n"
					+ "FROM Professor\n"
					+ "WHERE email = '" + email + "' and pw ='" + password +"'"
					+";";
			ResultSet professorSearchResult = statement.executeQuery(searchProfessorsql);
			
			if (professorSearchResult.next())
			{
				int idString = professorSearchResult.getInt("user_id");
				String passwordString = professorSearchResult.getString("pw");
				String emailString = professorSearchResult.getString("email");
				String fNameString = professorSearchResult.getString("fname");
				String lNameString = professorSearchResult.getString("lname");
				String schoolString = professorSearchResult.getString("schoolName");
				String deparmentString = professorSearchResult.getString("department");
				
				ProfessorUser currentProfessorUser = new ProfessorUser(idString, passwordString, emailString,fNameString,lNameString,schoolString,deparmentString);
				
				connection.close();
				return currentProfessorUser;
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
	
	
	public LinkedList<Professor> SearchProfessorForSignUp(String fname, String lname, String school)
	{
		
		LinkedList<Professor> professorList = new LinkedList<Professor>();
		
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);			
			Statement statement = connection.createStatement();

			String searchProfessorsql = "SELECT *\r\n"
					+ "FROM professor P\r\n"
					+ "WHERE P.fname LIKE '" + fname +"%' \r\n"
					+ "AND P.lname LIKE '" + lname +"%' \r\n"
					+ "AND P.schoolName Like '" + school + "%'\n"
					+ "AND P.pw = '' ;";	
			
			ResultSet ProfessorSearchResult = statement.executeQuery(searchProfessorsql);
	
			while(ProfessorSearchResult.next())
			{
				int user_IdString = ProfessorSearchResult.getInt("user_ID");
				String fnameString =  ProfessorSearchResult.getString("fname");
				String lnameString =  ProfessorSearchResult.getString("lname");
				String schoolString = ProfessorSearchResult.getString("schoolName");
				String emailString = ProfessorSearchResult.getString("email");
				Professor professor = new Professor(user_IdString, fnameString, lnameString, schoolString,emailString);
				professorList.add(professor);
					
			}
			
			connection.close();
	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return professorList;
	}


	public void UpdateProfessorPassword(String password, String id)
	{
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);			
			Statement statement = connection.createStatement();
			

			String updatePasswordSql = "UPDATE Professor P \n"
					+ "Set P.pw = '" + password + "' \n"
					+ "WHERE P.user_id = '" + id + "'" +";";
					
			
			statement.executeUpdate(updatePasswordSql);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				connection.close();
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
	/*
	public void AddProfessor(String password, String email, String fName, String lName, String school, String department)
	{
		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		
			String signupUser = "INSERT INTO Professor (pw, email, fname, lname, schoolName, department)"
					+ "VALUES(?,?,?,?,?,?);";
			
			PreparedStatement signupUserPstmt = connection.prepareStatement(signupUser);
			
			signupUserPstmt.setString(1, password);
			signupUserPstmt.setString(2, email);
			signupUserPstmt.setString(3, fName);
			signupUserPstmt.setString(4, lName);
			signupUserPstmt.setString(5, school);
			signupUserPstmt.setString(6, department);
			
			signupUserPstmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	*/

}
