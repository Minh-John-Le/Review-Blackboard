package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import Beans.SchoolReviewReport;

public class SchoolReviewReportDAO {

	public void save(SchoolReviewReport scrReport, String role) {
		try {
			InitialContext initialContext = new InitialContext();
			String dbUrl = (String) initialContext.lookup("java:comp/env/dbUrl");
			String dbUser = (String) initialContext.lookup("java:comp/env/dbUser");
			String dbPassword = (String) initialContext.lookup("java:comp/env/dbPassword");
			Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			if (role.equals("student")) {
				PreparedStatement stmt = con.prepareStatement("insert into stud_flags_screv(sid, scrid, text_cont, report_date) values(?,?,?,?)");
				stmt.setInt(1, scrReport.getReporterId());
				stmt.setInt(2, scrReport.getScrId());
				stmt.setString(3, scrReport.getBody());
				stmt.setDate(4, scrReport.getDate());

				stmt.executeUpdate();
			}
			else if (role.equals("professor")) {
				PreparedStatement stmt = con.prepareStatement("insert into prof_flags_screv(pid, scrid, text_cont, report_date) values(?,?,?,?)");
				stmt.setInt(1, scrReport.getReporterId());
				stmt.setInt(2, scrReport.getScrId());
				stmt.setString(3, scrReport.getBody());
				stmt.setDate(4, scrReport.getDate());

				stmt.executeUpdate();
			}

		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}

	}

}
