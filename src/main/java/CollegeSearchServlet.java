

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SchoolDAO;
import dao.SchoolReviewDAO;
import Beans.School;
import Beans.SchoolReview;

/**
 * Servlet implementation class CollegeSearchServlet
 */
@WebServlet("/collegeSearchServlet")
public class CollegeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SchoolReviewDAO schoolReviewDao = new SchoolReviewDAO();
	public SchoolDAO schoolDao = new SchoolDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/schoolPage.jsp");
		
		School school = schoolDao.findSchool(request.getParameter("school"));
		
		// School the user searched for is not in the database
		if (school.getName() == null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
			requestDispatcher.forward(request, response);
			return;
		}
		
		ArrayList<SchoolReview> reviews = schoolReviewDao.findSchoolReviews(school.getSchoolId());

		request.setAttribute("school", school);
		request.setAttribute("reviews", reviews);
		
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
