<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add Professor</title>
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
    <img src="Images/Professor.jpg" alt="Clipart of Professor" />
    <h1>Add a Professor</h1>
    <form method="post" action="AddProfessorServlet">
      <table class="center">
        <tr>
          <td>First Name:</td>
          <td><input name="fname" size="70" /></td>
        </tr>
        <tr>
          <td>Last Name:</td>
          <td><input name="lname" size="70" /></td>
        </tr>
        <tr>
          <td>Email:</td>
          <td><input name="email" size="70" /></td>
        </tr>
        <tr>
          <td>Department:</td>
          <td><input name="department" size="70" /></td>
        </tr>
        <tr>
          <td>School:</td>
          <td><input name="schoolName" size="70" /></td>
        </tr>
        <td/>
        <td><input type="submit" value="Submit" /></td>
      </table>
    </form>
    
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
    
  </body>
</html>
    