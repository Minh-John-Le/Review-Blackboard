package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Professor;
import Beans.ProfessorReview;
import Beans.ProfessorUser;
import Beans.StudentUser;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/supportTicketServlet")
public class SupportTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clickButton = request.getParameter("Button");
		
		HttpSession session = request.getSession();
		String textCont = request.getParameter("textContent").trim();
		String supportTag = request.getParameter("supportTag").trim();
		LinkedList<String> errlist = new LinkedList<String>();
		String author = "";
		
		
		
		
		
		
		if(clickButton.equals("Cancel"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
			requestDispatcher.forward(request, response);	

		}
		
		
		if (textCont == null || textCont.equals(""))
		{
			errlist.add("Report content cannot be empty!");
		}
		
		if (supportTag == null || supportTag.equals("---Issue Tag---"))
		{
			errlist.add("Please choose support issue!");
		}

		
		if (clickButton.equals("Submit"))
		{
			if(!errlist.isEmpty()) // no error prceed to home page
			{
				request.setAttribute("errlist", errlist);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("supportTicket.jsp");
				requestDispatcher.include(request, response);
				return;
			}
			
			if (session.getAttribute("userRole").equals("student")) {
				StudentUser curUser = (StudentUser)session.getAttribute("currentStudentUser");
				author = String.valueOf(curUser.getId());
				this.StudentSupportTicket(author, supportTag, textCont);
				
			}
			
			else {
				ProfessorUser curUser = (ProfessorUser)session.getAttribute("currentProfessorUser");
				author = String.valueOf(curUser.getId());
				this.ProfessorSupportTicket(author, supportTag, textCont);
			}
		}
			
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
		requestDispatcher.forward(request, response);
		
		return;
		
	}

	private void StudentSupportTicket(String studentID, String supportTag, String content)
	{
		try {
			
			LocalDateTime today = LocalDateTime.now();
			String todayString = today.toString();
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ProfessorSupportTicket(String professorID, String supportTag, String content)
	{
		try {
			
			LocalDateTime today = LocalDateTime.now();
			String todayString = today.toString();
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
