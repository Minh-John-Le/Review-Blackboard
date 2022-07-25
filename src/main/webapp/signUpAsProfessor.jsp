<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
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
    <h1>Sign Up</h1>
    <form id="Sign-up-form" action = "signUpAsProfessorServlet" method="post">
    
      <div>
        <label for="email">Email</label>
        <input type="text" name="email" class="u-full-width">
      </div>
      
      <div>
        <label for="password">Password</label>
        <input type="password" name="password" class="u-full-width">
      </div>

      <div>
        <label for="displayName">First Name</label>
        <input type="text" name="fName" class="u-full-width">
      </div>
      
      <div>
        <label for="displayName">Last Name</label>
        <input type="text" name="lName" class="u-full-width">
      </div>
      
       <div>
        <label for="displayName">School</label>
        <input type="text" name="school" class="u-full-width">
      </div>
      
      <div>
        <label for="displayName">Department</label>
        <input type="text" name="department" class="u-full-width">
      </div>

      <div>
        <input type="submit" value="Submit" name = "Button" >
        <input type="submit" value="Cancel" name = "Button" >
      </div>
      
      
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
    </form>
  </div>
  
 
</body>
</html>