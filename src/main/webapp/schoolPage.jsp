<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="Beans.School, Beans.SchoolReview, Beans.SchoolReviewComment, 
    DAO.SchoolReviewCommentDAO, Beans.StudentUser, Beans.ProfessorUser, java.util.*, java.sql.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
<title><% School school = (School)request.getAttribute("school"); 
out.println(school.getName() + " Reviews");%></title>
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
	
	.heading {
		display: inline-block;
	}
	
	.heading_right {
		display: inline-block;
		float: right;
	}
	
	h3 {
		font-size: 2rem;
		margin-bottom: 0;
	}
	
	li {
		margin-bottom: 0;
	}

	
  </style>
</head>
<body>
    <main>
    <div class="container">
      <span class = "heading">
      <form action="/Review_Blackboard/homePage.jsp">
      <input type="submit" value="Review Blackboard"/>
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
      <h1><%
      ArrayList<SchoolReview> reviews = (ArrayList<SchoolReview>)request.getAttribute("reviews");
      		 out.println(school.getName());%></h1>
      <h2>Average Quality Rating: <% 
      double average = 0;
      int count = 0;
      if (reviews.isEmpty()) out.println(average);
      else {
          for (SchoolReview review : reviews) {
        	  average += (double)review.getQuality();
        	  count++;
          }
          average /= count;
          
          out.println(String.format("%.2f", average) + "/5.00");
      }
          
          %></h2>
          
          <h2>Average Safety Rating: 
          <% 
          if (reviews.isEmpty()) out.println(average);
          else {
              average = 0;
              count = 0;
              for (SchoolReview review : reviews) {
            	  average += (double)review.getSafety();
            	  count++;
              }
              average /= count;
              
              out.println(String.format("%.2f", average) + "/5.00");
          }

      
      %></h2>
      <h2>Average Location Rating: 
      <% 
      if (reviews.isEmpty()) out.println(average);
      else {
          average = 0;
          count = 0;
          for (SchoolReview review : reviews) {
        	  average += (double)review.getLocation();
        	  count++;
          }
          average /= count;
          
          out.println(String.format("%.2f", average) + "/5.00");
      }

      
      %></h2>
      <h2>Average Infrastructure Rating:
      <% 
      if (reviews.isEmpty()) out.println(average);
      else {
          average = 0;
          count = 0;
          for (SchoolReview review : reviews) {
        	  average += (double)review.getInfrastructure();
        	  count++;
          }
          average /= count;
          
          out.println(String.format("%.2f", average) + "/5.00");
      }

      
      %>      
      </h2>
      <form action="searchCollegeReviewsServlet">
      	<h3 class="search-reviews">Search for Reviews</h3>
      	<div align="left">
        <label for="fromYear">From year:</label>
        <input type="text" id="fromYear" name="fromYear"/>
        <label for="toYear">To year:</label>
        <input type="text" id="toYear" name="toYear"/>
        </div>
        <div align="left">
        <div>
        <label for="quality">Quality at least:</label>
        <select name="quality" id="quality">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="safety">Safety at least:</label>
        <select name="safety" id="safety">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="location">Location at least:</label>
        <select name="location" id="location">
          <option value="" selected=>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="infrastructure">Infrastructure at least:</label>
        <select name="infrastructure" id="infrastructure">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        </div>
        <div>
        <label for="qualityBelow">Quality at most:</label>
        <select name="qualityBelow" id="qualityBelow">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="safetyBelow">Safety at most:</label>
        <select name="safetyBelow" id="safetyBelow">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="locationBelow">Location at most:</label>
        <select name="locationBelow" id="locationBelow">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="infrastructureBelow">Infrastructure at most:</label>
        <select name="infrastructureBelow" id="infrastructureBelow">
          <option value="" selected>---</option>
          <option value="5">5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        </div>
        </div>
        <input type="text" name="schoolId" value="<%=school.getSchoolId() %>" hidden />
        <input type="submit" value="Search" />
      </form>
      <form action="schoolReviews" method="post">
        <h3>Add a Review</h3>
        <textarea rows="5" cols="50" name="body"> </textarea>
        <br />
        <label for="reviewQuality">Quality:</label>
        <select name="quality" id="reviewQuality">
          <option value="5" selected>5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="reviewSafety">Safety:</label>
        <select name="safety" id="reviewSafety">
          <option value="5" selected>5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="reviewLocation">Location:</label>
        <select name="location" id="reviewLocation">
          <option value="5" selected>5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <label for="reviewInfrastructure">Infrastructure:</label>
        <select name="infrastructure" id="reviewInfrastructure">
          <option value="5" selected>5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
        <br />

        <input type="text" name="year" value="<%= new Date(new java.util.Date().getTime()) %>" hidden />
        <br />
        <input type="text" name="schoolId" value="<%=school.getSchoolId() %>" hidden />
        <input type="submit" value="submit" />
      </form>
      <div class="college-reviews">
      <hr>
        <% 
        SchoolReviewCommentDAO dao = new SchoolReviewCommentDAO();
		for (SchoolReview review : reviews) {
			out.println("<div class=\"review-container\">");
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
			out.println("<blockquote class=\"review\">");
			out.println(review.getBody());
			out.println("</blockquote>");
			out.println("<h3>Comments</h3>");
			ArrayList<SchoolReviewComment> comments = dao.findSchoolReviewComments(review.getScrId());
			out.println("<ul>");
			for (SchoolReviewComment comment : comments) {
				out.println("<li>" + comment.getBody() + "</li>");
			}
			out.println("</ul>");
			if (ses.getAttribute("userRole").equals("professor")) {
				out.println("<div>");
				out.println("<h3>Add a comment</h3>");
				out.println("<form action=\"schoolReviewCommentController\" method=\"get\">");
				out.println("<textarea rows=\"5\" cols=\"50\" name=\"body\"></textarea>");
				out.println("<input type=\"text\" name=\"year\" value=\"" + new Date(new java.util.Date().getTime()) + "\" hidden />");
				out.println("<input type=\"text\" name=\"scrId\" value=\"" + review.getScrId() + "\" hidden />");
				out.println("<br />");
				out.println("<input type=\"submit\" value=\"Comment\">");
				out.println("</form>");
				out.println("</div>");
			}
			out.println("<h3>Flag this review</h3>");
			out.println("<form action=\"schoolReviewReportController\" method=\"get\">");
			out.println("<textarea rows=\"5\" cols=\"50\" name=\"body\"></textarea><br />");
			out.println("<input type=\"text\" name=\"date\" value=\"" + new Date(new java.util.Date().getTime()) + "\" hidden />");
			out.println("<input type=\"text\" name=\"scrId\" value=\"" + review.getScrId() + "\" hidden />");
			out.println("<input type=\"text\" name=\"role\" value=\"" + ses.getAttribute("userRole") + "\" hidden />");
			out.println("<input type=\"submit\" value=\"Flag\">");
			out.println("</form>");
			out.println("</div>");	
		} %>
      </div>
      </div>
    </main>
</body>
</html>