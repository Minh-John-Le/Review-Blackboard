<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="Beans.SchoolReview, java.util.ArrayList, Beans.StudentUser, Beans.ProfessorUser"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
<title>Reviews</title>
<style>
  .review {
	  margin-left: 20px;
	  padding: 20px;
	}
	.tab {
		margin-left: 40px;
		display: inline-block;
	}
	
	label {
		display: inline-block;
	}
	
	h2 {
  		font-size: 30px;
	}
	
	.heading_right {
		display: inline-block;
		float: right;
	}
	
	h3 {
		font-size: 2rem;
		margin-bottom: 0;
	}
	
	form {
		display: inline-block;
	}
  </style>
</head>
<body>
<main>
<% ArrayList<SchoolReview> reviews = (ArrayList<SchoolReview>)request.getAttribute("reviews");
String name = (String)request.getAttribute("name");%>
<div class="container">
 <span class = "heading">
      <form action="/Review_Blackboard/homePage.jsp">
      <input type="submit" value="Review Blackboard"/>
      </form>
      <form action="collegeSearchServlet" method="get">
      <input type="hidden" name="school" value="<%= name %>">
      <input type="submit" value="Back to School">
      </form>
</span>
 	  <span class="heading_right">
 	  Hello, <% HttpSession ses = request.getSession();
 	  out.print((String)ses.getAttribute("name"));
 	   %>
 	   <form action="logOutServlet" method="get" style="display:inline-block;">
      <input type="submit" value="Log Out">
      </form>
 	  </span>
 	  
<h1>Matching Reviews</h1>
        <% 
		for (SchoolReview review : reviews) {
			out.println("<div>");
			out.println("<span>");
			out.println("<b>Quality:</b> " + review.getQuality());
			out.println("</span>");
			out.println("<span class=\"tab\">");
			out.println("<b>Safety:</b> " + review.getSafety());
			out.println("</span>");
			out.println("<span class=\"tab\">");
			out.println("<b>Location:</b> " + review.getLocation());
			out.println("</span>");
			out.println("<span class=\"tab\">");
			out.println("<b>Infrastructure:</b> " + review.getInfrastructure());
			out.println("</span>");
			out.println("<span class=\"tab\">");
			out.println("<b>Date posted:</b> " + review.getYear());
			out.println("</span>");			
			out.println("<br />");
			out.println("<br />");
			out.println("<blockquote class=\"review\">");
			out.println(review.getBody());
			out.println("</blockquote>");
			out.println("</div>");
			out.println("<br />");
		} 
		
		%>
</div>
</main>
</body>
</html>