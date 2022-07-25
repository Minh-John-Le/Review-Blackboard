package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}

