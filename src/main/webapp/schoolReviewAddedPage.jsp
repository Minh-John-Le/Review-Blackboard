<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="Beans.StudentUser, Beans.ProfessorUser"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
<title>Review Added</title>
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
<% String name = (String)request.getAttribute("name"); %>
<div class="container">
 <span class = "heading">
      <form action="homeServlet">
      <input type="submit" value="Review Blackboard" name="Button"/>
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
<h1>Review successfully created/updated!</h1>
</div>
</main>
</body>
</html>