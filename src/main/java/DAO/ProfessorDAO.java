package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;

import Beans.Professor;

public class ProfessorDAO {
	
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
}
