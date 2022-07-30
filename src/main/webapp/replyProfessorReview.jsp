<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
<%@page import = "java.util.LinkedList" %>
<%@page import = "Beans.ProfessorReview" %>
<%@page import = "Beans.Professor" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
  
  <title>Review Blackboard</title>
  <style>
  .review {
	  margin-left: 20px;
	  padding: 20px;
	}
	
	.comment {
	  margin-left: 40px;
	  padding: 20px;
	}

	.tab 
	{
		display: inline-block;
		margin-left: 40px;
	}
	
	h2 {
  		font-size: 30px;
	}
	
	.heading_right
	{
		display: inline-block;
		margin-left: 500px;
	}
	
	.textarea {
  		width: 800px;
 		height: 150px;
	}
  </style>
  
</head>
<body>


<!-- Review filter-->  
  <div class="container"> 	
 
    <form id="Sign-up-form" action = "replyProfessorReviewServlet" method="post">
	<h1> Reply </h1>
      
	<div>
		<textarea class = "textarea" name="textContent" id="message" ></textarea>
	</div>
   

      
      <span>
         <input type="submit" value="Submit" name = "Button">
      </span>
      
      <span class = "tab">
         <input type="submit" value="Cancel" name = "Button">
      </span>
      
      
      <hr>
      
      <%
      	List errList = (List) request.getAttribute("errlist");      		
      	if(errList != null)
      	{
      		for(Iterator it = errList.iterator(); it.hasNext();)
      		{
      			String error = (String) it.next();
      			%>
      			<font color ="red">
      			<li> <%=error%> </li>
      			</font>
      			<%
      		}
      	}
      %>
<!-- Review display block-->  
	
   	</form>
   </div>
</body>
</html>