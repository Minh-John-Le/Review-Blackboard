<%@page import = "java.util.List" %>
<%@page import = "Beans.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
  <style>
    .success, .error {
      color: white;
      padding: 5px;
      margin: 5px 0 15px 0;
    }

    .success {
      background: green;
    }

    .error {
      background: red;
    }
    
    .tab 
	{
		display: inline-block;
		margin-left: 40px;
	}
	
	.heading_right
	{
		display: inline-block;
		margin-left: 500px;
	}
    
  </style>
  <title>Review Blackboard</title>
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
   	
    <form id="Sign-up-form" action = "homeServlet" method="post">
    
    <span class = "heading">
 		<input type="submit" value="Review Blackboard" name = "Button">
 			
 	</span>
 	<span class = heading_right>
 		Hello, <%=user%>
 		<input type="submit" value="Log Out" name = "Button">
 	</span>
 	
    <h1>Review Blackboard</h1>
      <div>
        <label for="fname">Professor First Name</label>
        <input type="text" name="fname" class="u-full-width">
      </div>
      
      <div>
        <label for="lname">Professor Last Name</label>
        <input type="text" name="lname" class="u-full-width">
      </div>
      
      <div>
        <label for="School">School</label>
        <input type="text" name="school" class="u-full-width">
      </div>

      <div>
        <input type="submit" value="Search Professor" name = "Button">
        <input class = "tab" type="submit" value="Search School" name = "Button">
      </div>
      
    </form>
  </div>

</body>
</html>