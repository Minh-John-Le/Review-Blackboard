package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Beans.SchoolReviewComment;

public class SchoolReviewCommentDAO {
	
	public void save(SchoolReviewComment src) {
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("insert into comm_school_rev values(?,?,?,?)");
			stmt.setInt(1, src.getScrId());
			stmt.setInt(2, src.getProfessor());
			stmt.setDate(3, src.getDate());
			stmt.setString(4, src.getBody());
			stmt.executeUpdate();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<SchoolReviewComment> findSchoolReviewComments(int scrId) {
		ArrayList<SchoolReviewComment> comments = new ArrayList<>();
		
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			PreparedStatement stmt = con.prepareStatement("select * from comm_school_rev where scrid=?");
			stmt.setInt(1, scrId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				SchoolReviewComment comment = new SchoolReviewComment();
				
				comment.setScrId(rs.getInt(1));
				comment.setProfessor(rs.getInt(2));
				comment.setDate(rs.getDate(3));
				comment.setBody(rs.getString(4));
				
				comments.add(comment);
			}
			
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		
		return comments;
		
	}
	
}
