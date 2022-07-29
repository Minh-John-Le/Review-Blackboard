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

import Beans.School;
import Beans.SchoolReview;

public class SchoolDAO {
	
	public void save(School school) {
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement stmt = con.prepareStatement("insert into school(sname, street, city, state, zipcode) values(?,?,?,?,?)");
			stmt.setString(1, school.getName());
			stmt.setString(2, school.getStreet());
			stmt.setString(3, school.getCity());
			stmt.setString(4, school.getState());
			stmt.setString(5, school.getZip());
			stmt.executeUpdate();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}
	
	public School findSchool(String searchSchool) {
		School school = new School();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement stmt = con.prepareStatement("select * from school where sname=?");
			stmt.setString(1, searchSchool);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				school.setSchoolId(rs.getInt(1));
				school.setName(rs.getString(2));
				school.setStreet(rs.getString(3));
				school.setCity(rs.getString(4));
				school.setState(rs.getString(5));
				school.setZip(rs.getString(6));				
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return school;
	}
	
	public School findSchoolById(int searchSchoolId) {
		School school = new School();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement stmt = con.prepareStatement("select * from school where school_id=?");
			stmt.setInt(1, searchSchoolId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				school.setSchoolId(rs.getInt(1));
				school.setName(rs.getString(2));
				school.setStreet(rs.getString(3));
				school.setCity(rs.getString(4));
				school.setState(rs.getString(5));
				school.setZip(rs.getString(6));	
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return school;
	}
	
	public School findSchoolByReview(SchoolReview review) {
		School school = new School();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("select * from school where school_id=?");
			stmt.setInt(1, review.getSchoolId());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				school.setSchoolId(rs.getInt(1));
				school.setName(rs.getString(2));
				school.setStreet(rs.getString(3));
				school.setCity(rs.getString(4));
				school.setState(rs.getString(5));
				school.setZip(rs.getString(6));	
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return school;
		
	}
	
	
	public boolean doesSchoolExist(String sName)
	{
		
		InitialContext initialContext;
		
		
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			Statement statement = connection.createStatement();			
			String searchSchoolsql = "SELECT * "
					+ "FROM School "
					+ "WHERE sName = '" + sName + "';";
			ResultSet resultSet = statement.executeQuery(searchSchoolsql);
			
			
			if(resultSet.next())
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return false;
	}
	
	public void AddSchoolDAO(String sName, String street, String city, String state, String zipcode)
	{
		
		InitialContext initialContext;
		
		
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);		
			
			
		    String addschoolsql = "Insert Into school (sname, street, city, state, zipcode)\n"+ "VALUES(?,?,?,?,?);";
		    PreparedStatement addschoolPstmt = connection.prepareStatement(addschoolsql);
		    addschoolPstmt.setString(1, sName);
		    addschoolPstmt.setString(2, street);
		    addschoolPstmt.setString(3, city);
		    addschoolPstmt.setString(4, state);
		    addschoolPstmt.setString(5, zipcode);
		    addschoolPstmt.executeUpdate();
		    
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public LinkedList<School> SearchSchool (String schoolName)
	{
		LinkedList<School> schoolList = new LinkedList<School>();
		InitialContext initialContext;
		
		
		try {
			initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);	
			
			
			Statement statement = connection.createStatement();
			String searchSchoolsql = "SELECT *\r\n"
					+ "FROM school S\r\n"
					+ "WHERE S.sname LIKE '" + schoolName +"%' ;";
			
			ResultSet SchoolSearchResult = statement.executeQuery(searchSchoolsql);

			while(SchoolSearchResult.next())
			{
				int schoolID = SchoolSearchResult.getInt("school_id");
				String sname = SchoolSearchResult.getString("sname");
				String street = SchoolSearchResult.getString("street");
				String city = SchoolSearchResult.getString("city");
				String state = SchoolSearchResult.getString("state");		
				String zipcode = SchoolSearchResult.getString("zipcode");
				
				
				School school = new School(sname,street,city,state,zipcode,schoolID);
				schoolList.add(school);
				
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return schoolList;
	}
}

