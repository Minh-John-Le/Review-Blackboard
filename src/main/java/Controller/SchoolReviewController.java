package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.SchoolDAO;
import DAO.SchoolReviewDAO;
import Beans.ProfessorUser;
import Beans.SchoolReview;
import Beans.StudentUser;

/**
 * Servlet implementation class SchoolReviewController
 */
@WebServlet("/schoolReviews")
public class SchoolReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchoolReviewDAO dao = new SchoolReviewDAO();
	private SchoolDAO schoolDao = new SchoolDAO();
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String body = request.getParameter("body");
		int quality = Integer.parseInt(request.getParameter("quality"));
		int safety = Integer.parseInt(request.getParameter("safety"));
		int location = Integer.parseInt(request.getParameter("location"));
		int infrastructure = Integer.parseInt(request.getParameter("infrastructure"));
		Date year = Date.valueOf(request.getParameter("year"));
		int schoolId = Integer.parseInt(request.getParameter("schoolId"));
		int author;
		
		
		if (session.getAttribute("userRole").equals("student")) {
			StudentUser curUser = (StudentUser)session.getAttribute("currentStudentUser");
			author = curUser.getId();
		}
		
		else {
			ProfessorUser curUser = (ProfessorUser)session.getAttribute("currentProfessorUser");
			author = curUser.getId();
		}
		
		SchoolReview schoolReview = new SchoolReview();
		schoolReview.setYear(year);
		schoolReview.setBody(body);
		schoolReview.setAuthor(author);
		schoolReview.setSchoolId(schoolId);
		schoolReview.setLocation(location);
		schoolReview.setSafety(safety);
		schoolReview.setQuality(quality);
		schoolReview.setInfrastructure(infrastructure);
		
		if (dao.findSchoolReviewByAuthor(author) != null) {
			dao.update(schoolReview);
		}
		else {
			dao.save(schoolReview);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/schoolReviewAddedPage.jsp");
		request.setAttribute("name", schoolDao.findSchoolById(schoolId).getName());
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
