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
    
    
  </style>
  <title>Review Blackboard</title>
</head>
<body>
  <div class="container">
    <h1>Professor Search Result</h1>
    <form id="Sign-up-form" action = "professorSearchResultServlet" method="post">
      <table>
  		<tr>
    		<th> Name </th>
    		<th>School</th>
    		<th> View </th>
  		</tr>
		
  		<%

      	LinkedList<Professor> professorList = (LinkedList<Professor>) session.getAttribute("searchProfessorList");      		
      	if(professorList != null)
      	{
      		for(int i = 0 ; i < professorList.size(); i++)
      		{
      			String profName = professorList.get(i).getFname() + " " + professorList.get(i).getLname();
      			int user_Id = professorList.get(i).getUser_ID();
      			String schoolName = professorList.get(i).getSchoolName();
      			String view = "view" + String.valueOf(user_Id);
      			%>
      			
      			<tr>
   		 			<td> <%=profName%> </td>
    				<td> <%=schoolName %> </td>
    				<td>
    		        	<input type="submit" value="View" name = <%=view%> >
    				</td>
  				</tr>
      			<%
      		}
      	}
      	%>
  	</table>
      <div>
        <input type="submit" value="Back" name = "Button">
      </div>
      
    </form>
  </div>

</body>
</html>