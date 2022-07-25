package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import Beans.SchoolReview;

public class SchoolReviewDAO {
	

	public void save(SchoolReview schoolReview) {
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("insert into school_reviews(pub_date, text_cont, author, school_id, location, safety, quality, infrastructure) values(?,?,?,?,?,?,?,?)");
			stmt.setDate(1, schoolReview.getYear());
			stmt.setString(2, schoolReview.getBody());
			stmt.setInt(3, schoolReview.getAuthor());
			stmt.setInt(4, schoolReview.getSchoolId());
			stmt.setInt(5, schoolReview.getLocation());
			stmt.setInt(6, schoolReview.getSafety());
			stmt.setInt(7, schoolReview.getQuality());
			stmt.setInt(8, schoolReview.getInfrastructure());
			stmt.executeUpdate();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}

	}
	
	public void update(SchoolReview schoolReview) {
			try {
				InitialContext initialContext = new InitialContext();
				String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
				String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
				String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
				Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

				PreparedStatement stmt = con.prepareStatement("update school_reviews set pub_date=?, text_cont=?, location=?, safety=?, quality=?, infrastructure=? where author=?");
				stmt.setDate(1, schoolReview.getYear());
				stmt.setString(2, schoolReview.getBody());
				stmt.setInt(3, schoolReview.getLocation());
				stmt.setInt(4, schoolReview.getSafety());
				stmt.setInt(5, schoolReview.getQuality());
				stmt.setInt(6, schoolReview.getInfrastructure());
				stmt.setInt(7, schoolReview.getAuthor());
				stmt.executeUpdate();
			} catch (SQLException | NamingException e) {
				e.printStackTrace();
			}
	}

	public ArrayList<SchoolReview> findSchoolReviews(int schoolId) {
		ArrayList<SchoolReview> reviews = new ArrayList<>();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("select * from school_reviews where school_id=?");
			stmt.setInt(1, schoolId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				SchoolReview schoolReview = new SchoolReview();
				schoolReview.setScrId(rs.getInt(1));
				schoolReview.setYear(rs.getDate(2));
				schoolReview.setBody(rs.getString(3));
				schoolReview.setAuthor(rs.getInt(4));
				schoolReview.setSchoolId(rs.getInt(5));
				schoolReview.setLocation(rs.getInt(6));
				schoolReview.setSafety(rs.getInt(7));
				schoolReview.setQuality(rs.getInt(8));
				schoolReview.setInfrastructure(rs.getInt(11));
			
				reviews.add(schoolReview);
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return reviews;
	}
	
	public SchoolReview findSchoolReviewByAuthor(int author) {
		SchoolReview schoolReview = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("select * from school_reviews where author=?");
			stmt.setInt(1, author);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				schoolReview = new SchoolReview();
				schoolReview.setScrId(rs.getInt(1));
				schoolReview.setYear(rs.getDate(2));
				schoolReview.setBody(rs.getString(3));
				schoolReview.setAuthor(rs.getInt(4));
				schoolReview.setSchoolId(rs.getInt(5));
				schoolReview.setLocation(rs.getInt(6));
				schoolReview.setSafety(rs.getInt(7));
				schoolReview.setQuality(rs.getInt(8));
				schoolReview.setInfrastructure(rs.getInt(11));
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return schoolReview;
	}
	
	public SchoolReview findSchoolReviewById(int scrId) {
		SchoolReview schoolReview = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("select * from school_reviews where scrid=?");
			stmt.setInt(1, scrId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				schoolReview = new SchoolReview();
				schoolReview.setScrId(rs.getInt(1));
				schoolReview.setYear(rs.getDate(2));
				schoolReview.setBody(rs.getString(3));
				schoolReview.setAuthor(rs.getInt(4));
				schoolReview.setSchoolId(rs.getInt(5));
				schoolReview.setLocation(rs.getInt(6));
				schoolReview.setSafety(rs.getInt(7));
				schoolReview.setQuality(rs.getInt(8));
				schoolReview.setInfrastructure(rs.getInt(11));
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return schoolReview;
	}

	public ArrayList<SchoolReview> filterSchoolReviews(int schoolId, ArrayList<Integer> params) {
		ArrayList<SchoolReview> reviews = new ArrayList<>();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");

			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement stmt = con.prepareStatement(
					"select * from school_reviews where school_id=? and pub_date >= ? and pub_date <= ? and quality >= ? " + 
			"and quality <= ? and safety >= ? and safety <= ? and location >= ? and location <= ? and infrastructure >= ? and infrastructure <= ?");
			stmt.setInt(1, schoolId);
			stmt.setInt(2, params.get(0));
			stmt.setInt(3, params.get(1));
			stmt.setInt(4, params.get(2));
			stmt.setInt(5, params.get(3));
			stmt.setInt(6, params.get(4));
			stmt.setInt(7, params.get(5));
			stmt.setInt(8, params.get(6));
			stmt.setInt(9, params.get(7));
			stmt.setInt(10, params.get(8));
			stmt.setInt(11, params.get(9));
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				SchoolReview schoolReview = new SchoolReview();

				schoolReview.setScrId(rs.getInt(1));
				schoolReview.setYear(rs.getDate(2));
				schoolReview.setBody(rs.getString(3));
				schoolReview.setAuthor(rs.getInt(4));
				schoolReview.setSchoolId(rs.getInt(5));
				schoolReview.setLocation(rs.getInt(6));
				schoolReview.setSafety(rs.getInt(7));
				schoolReview.setQuality(rs.getInt(8));
				schoolReview.setInfrastructure(rs.getInt(11));
				reviews.add(schoolReview);
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		
		return reviews;
		
	}
}
