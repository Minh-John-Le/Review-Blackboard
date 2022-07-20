package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/professorSearchResultServlet")
public class ProfessorSearchResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get all parameter
		String clickButton = request.getParameter("Button");
		HttpSession session = request.getSession();
		
		LinkedList<Professor> professorList = (LinkedList<Professor>) (session.getAttribute("searchProfessorList"));
		int index = 0;
		
		
		if (clickButton != null)
		{
			// if click a button then process
			if (clickButton.equals("Home"))
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
				requestDispatcher.forward(request, response);
				return;
			}
		}
		
		
		// Check is user click any view button
		while(index < professorList.size())
		{
			String Id = String.valueOf(professorList.get(index).getUser_ID());
			String view = "view" + Id;
			
			if(request.getParameter(view) != null)
			{
				session.setAttribute("selectedProfessor", Id);
				ArrayList<ProfessorReview> reviewList = this.ProfessorReviewList(Id);
				session.setAttribute("professorReview", reviewList);
				
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("professorReview.jsp");		
				requestDispatcher.forward(request, response);
				return;
				
							
			}
			index++;			
		}
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			
			
				connection.close();
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private ArrayList<ProfessorReview> ProfessorReviewList(String professorID)
	{
		ArrayList<ProfessorReview> reviewList= new ArrayList<ProfessorReview>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
			Statement statement = connection.createStatement();
			String searchProfessorReviewsql = "SELECT distinct *\r\n"
					+ "FROM Prof_Reviews PR, Comm_prof_rev C\r\n"
					+ "WHERE PR.prof = '" + professorID + "' "
					+ "AND PR.prid = C.prid\r\n"
					+ "ORDER BY PR.pub_date;";


			ResultSet searchResult = statement.executeQuery(searchProfessorReviewsql);
			
		
			
			while(searchResult.next())
			{
				int  reviewIDString = searchResult.getInt("prid");
				String contentString = searchResult.getString("text_cont");
				int profID = searchResult.getInt("prof");
				int quality = searchResult.getInt("quality");
				int difficulty = searchResult.getInt("difficulty");
				String course_name = searchResult.getString("course_name");
				String class_type = searchResult.getString("class_type");
				String grade = searchResult.getString("grade");
				String year = searchResult.getString("Pyear");
				String semester = searchResult.getString("semester");
				String comment = searchResult.getString("C.text_cont");
				
				if (comment == null)
				{
					comment = "";
				}
				
				ProfessorReview review = new ProfessorReview(reviewIDString, contentString, profID, quality, difficulty, course_name, 
						class_type,grade, year, semester,comment);
				reviewList.add(review);
			}
			
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviewList;
		
	}

}
