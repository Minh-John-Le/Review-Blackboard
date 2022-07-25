package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PreInitServlet
 */
@WebServlet(urlPatterns = "/preInitServlet", loadOnStartup = 0)


public class PreInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;
    
    
	public void init(ServletConfig config)
	{		
			try {
				ServletContext context = config.getServletContext();
				System.out.println("init()");
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(context.getInitParameter("hostUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			
				java.sql.Statement statement = connection.createStatement();
				String databaseCreation = "CREATE DATABASE  IF NOT EXISTS ReviewBlackboardDB;";
				
				statement.executeUpdate(databaseCreation);
				connection.close();
				
				//-----------------------------
				
				connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				
				statement = connection.createStatement();
				String usertableDDL = "Create Table If Not Exists Student(\r\n"
						+ "user_id int AUTO_INCREMENT,\r\n"
						+ "password varchar(20) not null,\r\n"
						+ "email varchar(30) not null unique,\r\n"
						+ "fname varchar(30),\r\n"
						+ "lname varchar(30),\r\n"
						+ "major varchar(30),\r\n"
						+ "Primary key(user_id)\r\n"
						+ ");";
				statement.executeUpdate(usertableDDL);
				
				
				
				
				String SchoolDDL = " Create Table If Not Exists School(\r\n"
						+ "school_id int AUTO_INCREMENT,\r\n"
						+ "sname varchar(100) not null unique,\r\n"
						+ "street varchar(50),\r\n"
						+ "city varchar(50),\r\n"
						+ "state char(2),\r\n"
						+ "zipcode char(5),\r\n"
						+ "Primary key(school_id)\r\n"
						+ ");";
				statement.executeUpdate(SchoolDDL);
				
				
				String professorDDL = "Create Table If Not Exists Professor(\r\n"
						+ "user_id int AUTO_INCREMENT,\r\n"
						+ "pw varchar(20) not null,\r\n"
						+ "email varchar(30) not null unique,\r\n"
						+ "fname varchar(30),\r\n"
						+ "lname varchar(30),\r\n"
						+ "schoolName varchar(30),\r\n"
						+ "department varchar(30),\r\n"
						+ "Primary key(user_id)\r\n"
						+ ");;";
				statement.executeUpdate(professorDDL);
				
				String Prof_ReviewsDDL = " Create Table If Not Exists Prof_Reviews(\r\n"
						+ "prid int AUTO_INCREMENT,\r\n"
						+ "pub_date Varchar(30) not null,\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "author int not null,\r\n"
						+ "prof int not null,\r\n"
						+ "quality int,\r\n"
						+ "difficulty int,\r\n"
						+ "course_name varchar(30),\r\n"
						+ "class_type varchar(30),\r\n"
						+ "grade varchar(50),\r\n"
						+ "Pyear char(4),\r\n"
						+ "semester varchar(30),\r\n"
						+ "Primary key(prid),\r\n"
						+ "Foreign key(author) References Student(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(prof) References Professor(user_id) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Prof_ReviewsDDL);
				
				
				String School_ReviewsDDL = " Create Table If not Exists School_Reviews(\r\n"
						+ "scrid int AUTO_INCREMENT,\r\n"
						+ "pub_date Varchar(30) not null,\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "author int not null,\r\n"
						+ "school_id int not null,\r\n"
						+ "location int,\r\n"
						+ "safety int,\r\n"
						+ "quality int,\r\n"
						+ "from_year char(4),\r\n"
						+ "to_year char(4),\r\n"
						+ "infrastructure int,\r\n"
						+ "Primary key(scrid),\r\n"
						+ "Foreign key(author) References Student(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(school_id) References School(school_id) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(School_ReviewsDDL);
				
				
				String Comm_Prof_RevDDL = " Create Table If Not Exists Comm_Prof_Rev(\r\n"
						+ "prid int,\r\n"
						+ "professor int not null,\r\n"
						+ "pub_date Varchar(30),\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "primary key(professor, prid),\r\n"
						+ "Foreign key(professor) References Professor(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(prid) References Prof_Reviews(prid) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Comm_Prof_RevDDL);
				
				
				String Comm_School_RevDDL = " Create Table If Not Exists Comm_School_Rev(\r\n"
						+ "scrid int,\r\n"
						+ "professor int,\r\n"
						+ "pub_date Varchar(30),\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "primary key(professor , scrid),\r\n"
						+ "Foreign key(professor) References Professor(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(scrid) References School_Reviews(scrid) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Comm_School_RevDDL);
				
				
				String Prof_Flags_ScrevDDL = "Create Table If Not Exists Prof_Flags_Screv(\r\n"
						+ "reportID int AUTO_INCREMENT,\r\n"
						+ "pid int,\r\n"
						+ "scrid int,\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "report_date Varchar(30),\r\n"
						+ "primary key(ReportID),\r\n"
						+ "Foreign key(pid) References Professor(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(scrid) References School_Reviews(scrid) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Prof_Flags_ScrevDDL);
				
				
				
				String Stud_Flags_ScrevDDL = "Create Table If Not Exists Stud_Flags_Screv(\r\n"
						+ "reportID int AUTO_INCREMENT,\r\n"
						+ "sid int,\r\n"
						+ "scrid int,\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "report_date Varchar(30),\r\n"
						+ "primary key(ReportID),\r\n"
						+ "Foreign key(sid) References Student(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(scrid) References School_Reviews(scrid) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Stud_Flags_ScrevDDL);
				
				
				String Stud_Reports_PrevDDL = "Create Table If Not Exists Stud_Reports_Prev(\r\n"
						+ "reportID int AUTO_INCREMENT,\r\n"
						+ "sid int,\r\n"
						+ "prid int,\r\n"
						+ "text_cont varchar(500),\r\n"
						+ "report_date Varchar(30),\r\n"
						+ "primary key(reportId),\r\n"
						+ "Foreign key(sid) References Student(user_id) on delete cascade on update cascade,\r\n"
						+ "Foreign key(prid) References Prof_Reviews(prid) on delete cascade on update cascade\r\n"
						+ ");";
				statement.executeUpdate(Stud_Reports_PrevDDL);
				
				
				connection.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
