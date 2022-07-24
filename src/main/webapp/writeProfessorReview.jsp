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
 	<span class = "heading">
 		<input type="submit" value="Review Blackboard" name = "Button">
 			
 	</span>
 	<span class = heading_right>
 		Hello, Minh Le 
 		<input type="submit" value="Log Out" name = "Button">
 	</span>
 	
 	<%
 	Professor  professor = (Professor)(session.getAttribute("selectedProfessor"));
 	if(professor != null)
 	{
 		String professorName = professor.getFname() + " " + professor.getLname();
 		
 	%>
    <h1><%= professorName%></h1>
	<%
 	}
	%>

   
    <form id="Sign-up-form" action = "writeProfessorReviewServlet" method="post">
      
      <div  align="left">
        <b>Course:</b> <input type="text" name="course" >
      
        <b>From Year:</b> <input type= "number" name= "year">
       
         <select id = "semester" name = "semester" class = "tab">  
	          <option> ---Semester/Quarter--- </option>  
	          <option> Fall</option>  
	          <option> Winter </option> 
	          <option> Spring </option>
	          <option> Summer </option>     
        </select>
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