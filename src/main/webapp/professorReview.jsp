<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Review Blackboard</title>
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
      
    </form>
  </div>

</body>
</html>