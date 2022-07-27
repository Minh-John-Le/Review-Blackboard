<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
<%@page import = "java.util.LinkedList" %>
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
  
    <form id="Sign-up-form" action = "schoolSearchResultServlet" method="post">
    
    <span class = "heading">
 		<input type="submit" value="Review Blackboard" name = "Button">
 			
 	</span>
 	<span class = heading_right>
 		Hello, <%=user%>
 		<input type="submit" value="Log Out" name = "Button">
 	</span>
 	
    <h1>School Search Result</h1>
    
      <table style = "margin-left: 200px">
  		<tr>
    		<th> School </th>
    		<th>Street</th>
    		<th>City</th>
    		<th> State </th>
    		<th> Zip </th>
  		</tr>
		
  		<%

      	LinkedList<School> searchSchoolList = (LinkedList<School>) session.getAttribute("searchSchoolList");      		
      	if(searchSchoolList != null)
      	{
      		for(int i = 0 ; i < searchSchoolList.size(); i++)
      		{
      			School currentSchool = searchSchoolList.get(i);
      			
      			String schoolName = currentSchool.getName();
      			String street = currentSchool.getStreet();
      			String city = currentSchool.getCity();
      			String state = currentSchool.getState();
      			String zip = currentSchool.getZip();
      			String id = String.valueOf(currentSchool.getSchoolId());
      			String view = "view" + id;
      			
      			%>
      			
      			<tr>
   		 			<td> <%=schoolName%> </td>
    				<td> <%=street %> </td>
    				<td> <%=city %> </td>
    				<td> <%=state %> </td>
    				<td> <%=zip %> </td>
    				
    				<td>
    		        	<input type="submit" value="View" name = <%=view%> >
    				</td>
  				</tr>
      			<%
      		}
      	}
      	%>
  	</table>
      
      
    </form>
  </div>

</body>
</html>