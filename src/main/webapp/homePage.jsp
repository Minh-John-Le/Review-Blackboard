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
    <h1>Review Blackboard</h1>
    <form id="Sign-up-form" action = "homeServlet" method="post">
      <div>
        <label for="Professor">Professor</label>
        <input type="text" name="professor" class="u-full-width">
      </div>
      
      <div>
        <label for="School">School</label>
        <input type="text" name="school" class="u-full-width">
      </div>
      
      <div>
        <label for="School Zip">School Zip</label>
        <input type="text" name="zip" class="u-full-width">
      </div>

      <div>
        <input type="submit" value="Search School" name = "Button">
        <input type="submit" value="Search Professor" name = "Button">
      </div>
      
    </form>
  </div>

</body>
</html>