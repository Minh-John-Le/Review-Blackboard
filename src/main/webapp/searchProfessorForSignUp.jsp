<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
<%@page import = "java.util.LinkedList" %>
<%@page import = "Beans.Professor" %>
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
  </style>
  <title>Review Blackboard</title>
</head>
<body>
  <div class="container">
    <h1>Search For Professor</h1>
    <form id="Sign-up-form" action = "searchProfessorForSignUpServlet" method="post">
    

      <div>
        <label for="displayName">First Name</label>
        <input type="text" name="fname" class="u-full-width">
      </div>
      
      <div>
        <label for="displayName">Last Name</label>
        <input type="text" name="lname" class="u-full-width">
      </div>
      
       <div>
        <label for="displayName">School</label>
        <input type="text" name="school" class="u-full-width">
      </div>

      <div>
        <input type="submit" value="Submit" name = "Button" >
        <input type="submit" value="Add myself" name = "Button" class = "tab" >
        <input type="submit" value="Cancel" name = "Button" class = "tab">
      </div>
      
    
		
  		<%

      	LinkedList<Professor> professorList = (LinkedList<Professor>) session.getAttribute("searchProfessorList");      		
      	if(professorList != null)
      	{
      		%>
      	<table style = "margin-left: 200px">
  			<tr>
	    		<th> Name </th>
	    		<th>School</th>
	    		<th>Email</th>
	    		<th> Select </th>
	  		</tr>
      		
      		<%
      		for(int i = 0 ; i < professorList.size(); i++)
      		{
      			String profName = professorList.get(i).getFname() + " " + professorList.get(i).getLname();
      			int user_Id = professorList.get(i).getUser_ID();
      			String schoolName = professorList.get(i).getSchoolName();
      			String select = "select" + String.valueOf(user_Id);
      			String email = professorList.get(i).getEmail();
      			%>
      			
      			<tr>
   		 			<td> <%=profName%> </td>
    				<td> <%=schoolName %> </td>
    				<td> <%=email %> </td>
    				<td>
    		        	<input type="submit" value="View" name = <%=select%> >
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