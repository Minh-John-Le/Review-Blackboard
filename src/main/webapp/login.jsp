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
  <title>Blackboard Review</title>
</head>
<body>
  <div class="container">
    <h1>Log In</h1>
    <form id="Sign-up-form" action = "loginServlet" method="post">
      <div>
        <label for="email">Email</label>
        <input type="text" name="email" class="u-full-width">
      </div>
      
      <div>
        <label for="password">Password</label>
        <input type="text" name="password" class="u-full-width">
      </div>

      <div>
        <input type="submit" value="Log In" name = "Button">
        <input type="submit" value="Sign Up" name = "Button">
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