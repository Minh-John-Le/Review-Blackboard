<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
<%@page import = "java.util.LinkedList" %>
<%@page import = "Beans.ProfessorReview" %>
<%@page import = "Beans.Professor" %>
<%@page import = "Beans.*" %>


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
  </style>
  
</head>
<body>

<%
 String user = "";
	if(session.getAttribute("currentStudentUser") != null)
	{
		StudentUser student = (StudentUser) session.getAttribute("currentStudentUser");
		user = student.getfName() + " " + student.getlName();
	}
	
	if(session.getAttribute("currentProfessorUser") != null)
	{
		ProfessorUser professor = (ProfessorUser) session.getAttribute("currentProfessorUser");
		user = professor.getfName() + " " + professor.getlName();
	}

%>
<!-- Review filter-->  
  <div class="container">
 	
 	
 	
    <form id="Sign-up-form" action = "professorReviewServlet" method="post">
    
    <span class = "heading">
 		<input type="submit" value="Review Blackboard" name = "Button">
 			
 	</span>
 	<span class = heading_right>
 	Hello, <%=user %>
 		<input type="submit" value="Log Out" name = "Button">
 	</span>
 	
    
    <%
 		Professor  professor = (Professor) (session.getAttribute("selectedProfessor"));
 		String professorName = professor.getFname() + " " + professor.getLname();
 		String avgD = String.valueOf(professor.getAvgDifficulty());
 		String avgQ = String.valueOf(professor.getAvgQuality());	
 	
 	%>
 	
    <h1><%= professorName%></h1>
    <h2> Average Quality: <%= avgD %></h2>
    <h2>  Average Difficulty: <%= avgQ %></h2>
    
    
    
      <div  align="left">
        <b>Course:</b> <input type="text" name="course" >
      
        <b>From Year:</b> <input type= "text" name= "fromYear">
        
        <b>To Year:</b> <input type = "text" name = "toYear">
         
      </div>   
       
           
<!-- Review filter-->  
      <div align="left">
        <select id = "quality" name = "quality"  >  
          <option> ---Quality--- </option>  
          <option> 1 </option>  
          <option> 2 </option> 
          <option> 3 </option>
          <option> 4 </option>
          <option> 5 </option>      
        </select> 
                
        <select id = "difficulty" name = "difficulty" class = "tab">  
          <option> ---Difficulty--- </option>  
          <option> 1 </option>  
          <option> 2 </option> 
          <option> 3 </option>
          <option> 4 </option>
          <option> 5 </option>      
        </select>
        
        <select id = "classStyle" name = "classStyle" class = "tab">  
          <option> ---Class Style--- </option>  
          <option> In Person </option>
          <option> Online </option>  
          <option> Hybrid </option>      
        </select>   
               
        <select id = "grade" name = "grade" class = "tab">  
          <option> ---Grade--- </option>  
          <option> Not Sure Yet </option>
          <option> A </option>
          <option> B </option>  
          <option> C </option> 
          <option> D </option> 
          <option> E </option> 
          <option> F </option>             
        </select>
                  
      </div>
      
      <span>
         <input type="submit" value="Submit" name = "Button">
      </span>
      
      <%
      if(session.getAttribute("userRole").equals("student"))
		{
      %>
      
      <span class = "tab">
       	<input type="submit" value="Write Review" name = "Button">
      </span>
      <%
		}
      %>
     
      
      
      <hr>
<!-- Review display block-->  
	<%
		LinkedList<ProfessorReview> reviewList = (LinkedList<ProfessorReview>) (session.getAttribute("professorReview"));      		
      	Professor selectedProfessor = (Professor) session.getAttribute("selectedProfessor");
      	String selectedProfessorID = String.valueOf(selectedProfessor.getUser_ID());
	
		if(reviewList != null)
      	{
      		for (int i = 0; i < reviewList.size(); i++)
      		{
      			ProfessorReview review = reviewList.get(i);
      			String id = String.valueOf(review.getReviewId());
      			String difficulty = String.valueOf(review.getDifficulty());
      			String quality = String.valueOf(review.getQuality());
      			String course = review.getCourseName();
      			String grade = review.getGrade();
      			String year = review.getYear();
      			String semester = review.getSemester();
      			String classType = review.getClass_type();
      			String content = review.getContent();
      			String comment = review.getComment();
      			String report = "report" + id;
      			String reply = "reply" + id;
      			
      			
      			%>
      			<div> 
			   	<span> <b>Quality: </b> <%= quality%>  </span>
			
			   	<span class = "tab"> <b>Difficulty: </b> <%= difficulty%>  </span>
			   	
			   	<span class = "tab"> <b>Course: </b> <%= course%> </span>
			   	
			   	<span class = "tab"> <b>Grade: </b> <%= grade%> </span>
			   	
			   	<span class = "tab"> <b>Year: </b> <%= year%> </span>
				
			   	<span class = "tab"> <b>Quarter/Semester: </b> <%= semester%> </span>
			   	<br></br>
			   	<span> <b>Class Style: </b> <%= classType%> </span>
			   	
			   	
			   	
			  	<blockquote class = "review">
			  	<%=content%>
			  	</blockquote>
			  	
			  	
			  	<%
			  		if(session.getAttribute("userRole").equals("student"))
			  		{
			  	%>
			  	<div align="right">
			       	<input type="submit" value= "Report" name = <%= report%>>
			    </div>
			   	<%
      				}		
			   	%>
			   	
			   	<%
			  		if(session.getAttribute("userRole").equals("professor"))
			  		{
			  			ProfessorUser professorUser = (ProfessorUser)(session.getAttribute("currentProfessorUser"));
			  			String userID = String.valueOf(professorUser.getId());
			  			if(userID.equals(selectedProfessorID))
			  			{
			  	%>
			  	<div align="right">
			       	<input type="submit" value= "Reply" name = <%=reply%>>
			    </div>
			   	<%
			  			}
      				}		
			   	%>
  
  				</div>
  				
  				<% 
			  		if(!comment.equals(""))
			  		{
			  			
			  	%>
			  	<div class = "tab">
			  		<b><%="Professor "+ professorName + " reply" %>: </b>
			  	</div>
					  	<blockquote class = "comment">
					  	<%=comment%>
					  	</blockquote>
			  	<%
			  		}
			  	%>
			  	 <hr>
      			<%
      		}
      	}
      %>

   	</form>
   </div>
</body>
</html>