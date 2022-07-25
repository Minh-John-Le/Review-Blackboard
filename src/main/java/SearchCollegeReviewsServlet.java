

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SchoolDAO;
import dao.SchoolReviewDAO;
import Beans.SchoolReview;

/**
 * Servlet implementation class SearchCollegeReviewsServlet
 */
@WebServlet("/searchCollegeReviewsServlet")
public class SearchCollegeReviewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SchoolDAO schoolDao = new SchoolDAO();
	public SchoolReviewDAO schoolReviewDao = new SchoolReviewDAO();
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int schoolId = Integer.parseInt(request.getParameter("schoolId"));
		ArrayList<Integer> params = new ArrayList<>();
		int fromYear = 0;
		int toYear = Integer.MAX_VALUE;
		int quality = 0;
		int qualityBelow = Integer.MAX_VALUE;
		int safety = 0;
		int safetyBelow = Integer.MAX_VALUE;
		int location = 0;
		int locationBelow = Integer.MAX_VALUE;
		int infrastructure = 0;
		int infrastructureBelow = Integer.MAX_VALUE;
		
		if (request.getParameter("fromYear") != "") {
			fromYear = Integer.parseInt(request.getParameter("fromYear"));
		}
		if (request.getParameter("toYear") != "") {
			toYear = Integer.parseInt(request.getParameter("toYear"));
		}
		if (request.getParameter("quality") != "") {
		    quality = Integer.parseInt(request.getParameter("quality"));
		}
		if (request.getParameter("qualityBelow") != "") {
		    qualityBelow = Integer.parseInt(request.getParameter("qualityBelow"));
		}
		if (request.getParameter("safety") != "") {
			safety = Integer.parseInt(request.getParameter("safety"));
		}
		if (request.getParameter("safetyBelow") != "") {
			safetyBelow = Integer.parseInt(request.getParameter("safetyBelow"));
		}
		if (request.getParameter("location") != "") {
			location = Integer.parseInt(request.getParameter("location"));
		}
		if (request.getParameter("locationBelow") != "") {
			locationBelow = Integer.parseInt(request.getParameter("locationBelow"));
		}
		if (request.getParameter("infrastructure") != "") {
		    infrastructure = Integer.parseInt(request.getParameter("infrastructure"));
		}
		if (request.getParameter("infrastructureBelow") != "") {
		    infrastructureBelow = Integer.parseInt(request.getParameter("infrastructureBelow"));
		}
		params.add(fromYear);
		params.add(toYear);
		params.add(quality);
		params.add(qualityBelow);
		params.add(safety);
		params.add(safetyBelow);
		params.add(location);
		params.add(locationBelow);
		params.add(infrastructure);
		params.add(infrastructureBelow);
		
		ArrayList<SchoolReview> reviews = schoolReviewDao.filterSchoolReviews(schoolId, params);
		
		RequestDispatcher rd = request.getRequestDispatcher("/schoolReviewsPage.jsp");
		request.setAttribute("reviews", reviews);
		request.setAttribute("name", schoolDao.findSchoolById(schoolId).getName());
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
