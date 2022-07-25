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
import Beans.StudentUser;
import dao.SchoolDAO;
import dao.SchoolReviewCommentDAO;
import dao.SchoolReviewDAO;

/**
 * Servlet implementation class SchoolReviewCommentController
 */
@WebServlet("/schoolReviewCommentController")
public class SchoolReviewCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchoolReviewCommentDAO dao = new SchoolReviewCommentDAO();
	private SchoolDAO schoolDao = new SchoolDAO();
	private SchoolReviewDAO schoolReviewDao = new SchoolReviewDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String body = request.getParameter("body");
		int scrId = Integer.parseInt(request.getParameter("scrId"));
		Date year = Date.valueOf(request.getParameter("year"));
		ProfessorUser curUser = (ProfessorUser)session.getAttribute("currentProfessorUser");
		int professor = curUser.getId();
		
		SchoolReviewComment src = new SchoolReviewComment();
		src.setScrId(scrId);
		src.setProfessor(professor);
		src.setDate(year);
		src.setBody(body);
		
		dao.save(src);
		
		RequestDispatcher rd = request.getRequestDispatcher("/schoolReviewCommentPage.jsp");
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
