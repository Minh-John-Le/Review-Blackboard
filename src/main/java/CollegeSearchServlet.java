

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.SchoolDAO;
import DAO.SchoolReviewDAO;
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
		
		HttpSession session = request.getSession();
		School school = schoolDao.findSchool((String) session.getAttribute("selectSchoolName"));
		
		// School the user searched for is not in the database
		if (school.getName() == null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
			requestDispatcher.forward(request, response);
			return;
		}
		
		ArrayList<SchoolReview> reviews = schoolReviewDao.findSchoolReviews(school.getSchoolId());

		session.setAttribute("school", school);
		session.setAttribute("reviews", reviews);
		
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
