<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>
<%@page import = "java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add School</title>
    <style>
      body {
        font-family: Arial, Helvetica, sans-serif;
      }
      * {
        box-sizing: border-box;
      }
      input[type="text"],
      select,
      textarea {
        width: 100%;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        margin-top: 6px;
        margin-bottom: 16px;
        resize: vertical;
      }
      input[type="submit"] {
        background-color: #04aa6d;
        color: white;
        padding: 12px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }
      input[type="submit"]:hover {
        background-color: #45a049;
      }
      img {
        width: 500px;
        border-radius: 100px;
        margin-left: 40px;
        margin-right: 40px;
        object-fit: cover;
      }
      .center {
        margin-left: auto;
        margin-right: auto;
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;

      }
      td {
        font-weight: bold;
        font-size: large;
      }
      body {
        text-align: center;
      }
    </style>
  </head>
  <body>
    <img src="./Images/College.png" alt="Image of School"/>
    <h1>Add a New School</h1>
    <form method="post" action="addSchoolServlet">
      <table class="center">
        <tr>
          <td>Name :</td>
          <td><input name="sname" size="70" /></td>
        </tr>
        <tr>
          <td colspan="2">Enter the Address</td>
        </tr>
        <tr>
          <td>Street:</td>
          <td><input name="street" size="70" /></td>
        </tr>
        <tr>
          <td>City:</td>
          <td><input name="city" size="70" /></td>
        </tr>
        <tr>
          <td>State:</td>
          <td><input name="state" size="70" /></td>
        </tr>
        <tr>
          <td>Zipcode:</td>
          <td><input name="zipcode" size="70" /></td>
        </tr>
        <td />
        <td><input type="submit" value="Submit" /></td>
      </table>
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
  </body>
</html>
