package Controller;

import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.ProfessorUser;
import Beans.StudentUser;
import DAO.SupportTicketDAO;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/supportTicketServlet")
public class SupportTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupportTicketDAO supportTicketDAO = new SupportTicketDAO();
	
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
				supportTicketDAO.AddStudentSupportTicket(author, supportTag, textCont);
				
			}
			
			else {
				ProfessorUser curUser = (ProfessorUser)session.getAttribute("currentProfessorUser");
				author = String.valueOf(curUser.getId());
				supportTicketDAO.AddProfessorSupportTicket(author, supportTag, textCont);
			}
		}
			
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage.jsp");		
		requestDispatcher.forward(request, response);
		
		return;
		
	}

	


}
