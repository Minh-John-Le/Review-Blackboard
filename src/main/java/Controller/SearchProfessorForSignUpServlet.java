package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class SearchProfessorForSignUp
 */
@WebServlet("/searchProfessorForSignUpServlet")
public class SearchProfessorForSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	
	public void init(ServletConfig config)
	{				
		context = config.getServletContext();		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get all parametter
			String clickButton = request.getParameter("Button");
			String fname = request.getParameter("fname").trim();
			String lname = request.getParameter("lname").trim();
			String school = request.getParameter("school").trim();
			
			LinkedList<Professor> professorList = new LinkedList<Professor>();
			
			HttpSession session = request.getSession();
			
			try 
			{
						
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
						context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
				
				if(clickButton != null && clickButton.equals("Submit"))
				{
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
					
					if (professorList.size() > 0)
					{
						session.setAttribute("searchProfessorList", professorList);
					}
					else
					{
						session.setAttribute("searchProfessorList", null);
					}
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchProfessorForSignUp.jsp");
					requestDispatcher.include(request, response);
					connection.close();
					return;
					
		
				}
				
				if(clickButton != null && clickButton.equals("Cancel"))
				{
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
					requestDispatcher.include(request, response);
					connection.close();
					return;
				}
				
				
				if(clickButton != null && clickButton.equals("Add myself"))
				{
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("signUpAsProfessor.jsp");
					requestDispatcher.include(request, response);
					connection.close();
					return;
				}
					
				
				
				LinkedList<Professor> currentProfessorList = (LinkedList<Professor>) (session.getAttribute("searchProfessorList"));
				int index = 0;
				while(index < currentProfessorList.size())
				{
					String Id = String.valueOf(currentProfessorList.get(index).getUser_ID());
					String select = "select" + Id;
					
					if(request.getParameter(select) != null)
					{

						session.setAttribute("selectedProfessor", currentProfessorList.get(index));									
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("setUpProfessorPassword.jsp");		
						requestDispatcher.forward(request, response);
						
						return;
								
					}
					index++;			
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		
	}

}
