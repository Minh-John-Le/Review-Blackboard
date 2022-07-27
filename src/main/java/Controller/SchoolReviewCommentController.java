package Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

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
import DAO.SchoolDAO;
import DAO.SchoolReviewCommentDAO;
import DAO.SchoolReviewDAO;

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
		int professor = (int) session.getAttribute("userId");
		boolean commented = false;
		
		SchoolReviewComment src = new SchoolReviewComment();
		src.setScrId(scrId);
		src.setProfessor(professor);
		src.setDate(year);
		src.setBody(body);
		
		if (dao.findSchoolReviewCommentByAuthor(professor) != null) {
			ArrayList<SchoolReviewComment> comments = dao.findSchoolReviewCommentByAuthor(professor);
			for (SchoolReviewComment comment : comments) {
				if (comment.getScrId() == scrId) {
					dao.update(src);
					commented = true;
				}
			}
		}
		if (commented == false) {
			dao.save(src);
		}
		
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
