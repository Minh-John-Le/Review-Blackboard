package Controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.ProfessorUser;
import Beans.SchoolReview;
import Beans.SchoolReviewComment;
import Beans.SchoolReviewReport;
import Beans.StudentUser;
import dao.SchoolDAO;
import dao.SchoolReviewDAO;
import dao.SchoolReviewReportDAO;

/**
 * Servlet implementation class SchoolReviewReportController
 */
@WebServlet("/schoolReviewReportController")
public class SchoolReviewReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchoolReviewReportDAO dao = new SchoolReviewReportDAO();
	private SchoolReviewDAO schoolReviewDao = new SchoolReviewDAO();
	private SchoolDAO schoolDao = new SchoolDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String body = request.getParameter("body");
		int scrId = Integer.parseInt(request.getParameter("scrId"));
		Date date = Date.valueOf(request.getParameter("date"));
		
		int reporter;
		
		
		if (session.getAttribute("userRole").equals("student")) {
			StudentUser curUser = (StudentUser)session.getAttribute("currentStudentUser");
			reporter = curUser.getId();
		}
		
		else {
			ProfessorUser curUser = (ProfessorUser)session.getAttribute("currentProfessorUser");
			reporter = curUser.getId();
		}
		
		SchoolReviewReport report = new SchoolReviewReport();
		report.setScrId(scrId);
		report.setReporterId(reporter);
		report.setDate(date);
		report.setBody(body);
		
		dao.save(report, (String)session.getAttribute("userRole"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/schoolReviewReportedPage.jsp");
		SchoolReview review = schoolReviewDao.findSchoolReviewById(scrId);
		request.setAttribute("name", schoolDao.findSchoolByReview(review).getName());
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
